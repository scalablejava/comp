package com.scalablejava.comp.datalayer

import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}
import java.sql.Date

/**
 * Created by IntelliJ IDEA.
 * Sport: roberto
 * Date: 6/09/11
 * Time: 6:15 AM
 * To change this template use File | Settings | File Templates.
 */

object Team extends Table[(Long, String, Long, Date)]("Team") {
  def id = column[Long]("id", O NotNull, O PrimaryKey, O AutoInc)
  def name = column[String]("name")
  def createdBy = column[Long]("createdBy")
  def createdOn = column[Date]("createdOn")

  def fkTeamPerson = foreignKey("fk_team_person", createdBy, Person)(_.id)

  def * = id ~ name ~ createdBy ~ createdOn

}