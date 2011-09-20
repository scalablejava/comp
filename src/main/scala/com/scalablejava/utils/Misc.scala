package com.scalablejava.utils

import database.SqlReturn

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 11/09/11
 * Time: 7:13 AM
 * To change this template use File | Settings | File Templates.
 */

object Misc {
  def validateEmailAddress(emailAddress: String): Boolean = {
    return emailAddress.matches("""([\w\d\-\_]+)(\+\d+)?@([\w\d\-\.]+)""")
  }

  def validatePassword(password: String): Boolean = {
    return password.matches("""^.*(?=.{6,})(?=.*[a-zA-Z])(?=.*[\d\W]).*$""")
  }

  def addSqlErrorMessage(sqlReturn: SqlReturn): String = {
    sqlReturn.code match {
      case _ => return sqlReturn.message + ". (Error Code: " + sqlReturn.code + ")"
    }
  }

  def daysOfTheWeek = List((1,"Monday"), (2, "Tuesday"), (3, "Wednesday"), (4, "Thursday"),
    (5, "Friday"), (6, "Saturday"), (6, "Sunday"))

}