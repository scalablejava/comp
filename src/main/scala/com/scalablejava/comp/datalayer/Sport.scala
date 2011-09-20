package com.scalablejava.comp.datalayer

import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 5/09/11
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */

object Sport extends Table[(Long, String)]("Sport") {
  def id = column[Long]("id", O NotNull, O PrimaryKey, O AutoInc)
  def name = column[String]("name")

  def * = id ~ name
}