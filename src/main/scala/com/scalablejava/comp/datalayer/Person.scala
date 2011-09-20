package com.scalablejava.comp.datalayer

import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}

/**
 * Created by IntelliJ IDEA.
 * Sport: roberto
 * Date: 5/09/11
 * Time: 11:35 PM
 * To change this template use File | Settings | File Templates.
 */

object Person extends Table[(Long, String, String, String, String, String, String, String, String)]("Person") {
  def id = column[Long]("id", O NotNull, O PrimaryKey, O AutoInc)
  def fname = column[String]("fname")
  def lname = column[String]("lname")
  def phone = column[String]("phone")
  def mobile = column[String]("mobile")
  def email = column[String]("email")
  def address = column[String]("address")
  def username = column[String]("username")
  def password = column[String]("password")
  def * = id ~ fname ~ lname ~ phone ~ mobile ~ email ~ address ~ username ~ password
}