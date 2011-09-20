package com.scalablejava.comp.weblayer.filters

import com.scalablejava.comp.logiclayer.PersonService
import com.scalablejava.utils.database.SqlReturn
import com.scalablejava.utils.{Security, Misc}
import collection.mutable.ArrayBuffer

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 10/09/11
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */

class RegisterFilter extends CompBaseFilter {

  var values = ("", "")


  get("/register") {

    templateEngine.layout("/WEB-INF/scalate/templates/login.ssp",
      Map(
        "validationErrors" -> validationErrors,
        "values" -> values
      )
    )
  }

  def validate(email: String, username: String, password: String, re_password: String): Boolean = {

    validationErrors = ""
    var validations = new ArrayBuffer[String]()
    if (!Misc.validateEmailAddress(email)) {
      validations.append("Email address is not valid")
    }
    if (!Misc.validatePassword(password)) {
      validations.append("Password must be at least 8 characters in length, containing at least 1 letter" +
                         "and 1 number or symbol.")
    }
    if (!password.equals(re_password)) {
      validations.append("re-type password does not match password")
    }
    if (username.equals("")) {
      validations.append("Username is required")
    }

    validations.foreach((message: String) => {
      validationErrors += "<li>" + message + "</li>"
    })

    return validationErrors.equals("")
  }

  post("/register") {

    var nextUrl: String = "/register"

    values = (params("username"), params("email"))

    if (validate(params("email"), params("username"), params("password"), params("re-password"))) {
      var encryptedPassword: String = Security.byteArrayToHexString(Security.computeHash(params("password")))

      val person: com.scalablejava.comp.bo.Person = new com.scalablejava.comp.bo.Person(-1,
      "", "", "", "", params("email"),
      "", params("username"), encryptedPassword)

      val service: PersonService = new PersonService
      val exist: (Boolean, Boolean) = service.isUsernameOrEmailExist(params("username"),params("email"))
      exist._1
      if (exist._1) {
        validationErrors += "Username already exists"
      }
      if (exist._2) {
        validationErrors += "Email already exists"
      }
      if (!exist._1 && !exist._2) {
        val result: SqlReturn = service.save(person)
        if (result.code == -1) {
          session.setAttribute("user", person)
          nextUrl = "/home"
        } else {
          validationErrors += Misc.addSqlErrorMessage(result)
        }
      }
    }
    redirect(nextUrl)

  }

  get("/register/welcome") {
    <h1>Welcome to Comp</h1>
    <p>Click <a href="/home">here</a> to go to your Home page</p>

  }

}