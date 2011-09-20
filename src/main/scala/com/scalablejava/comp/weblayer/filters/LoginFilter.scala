package com.scalablejava.comp.weblayer.filters

import com.scalablejava.comp.logiclayer.PersonService
import com.scalablejava.utils.Security
import com.scalablejava.comp.bo.Person
/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 10/09/11
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */

class LoginFilter extends CompBaseFilter {

  get("/") {
    redirect("/login")
  }
  get("/login") {
    templateEngine.layout("/WEB-INF/scalate/templates/login.ssp",
      Map(
        "validationErrors" -> validationErrors,
        "values" -> ("","")
      )
    )

  }

  post("/login") {
    val service: PersonService = new PersonService
    var encryptedPassword: String = Security.byteArrayToHexString(Security.computeHash(params("password")))

    val person: Person = service.get(params("username"), encryptedPassword)
    if (person == null) {
      redirect("/login")
    } else {
      session.setAttribute("user", person)
      redirect("/home")
    }

  }

}