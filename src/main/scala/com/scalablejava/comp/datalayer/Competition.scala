package com.scalablejava.comp.datalayer

import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}
import java.sql.Date

/**
 * Created by IntelliJ IDEA.
 * Sport: roberto
 * Date: 6/09/11
 * Time: 6:28 AM
 * To change this template use File | Settings | File Templates.
 */

object Competition extends Table[(Long, String, Long, Date, Long, Date, Int, Int, String, String, Double)]("Competition") {
  def id = column[Long]("id", O NotNull, O PrimaryKey, O AutoInc)
  def name = column[String]("name")
  def sportId = column[Long]("sportId")
  def startsOn = column[Date]("startsOn")
  def createdBy = column[Long]("createdBy")
  def createdOn = column[Date]("createdOn")
  def numberOfTeams = column[Int]("numberOfTeams")
  def matchesBetweenTeams = column[Int]("matchesBetweenTeams")
  def billingTo = column[String]("billingTo")
  def billingAddress = column[String]("billingAddress")
  def billingAmount = column[Double]("billingAmount")

  def fkCompetitionSport = foreignKey("fk_competition_sport", sportId, Sport)(_.id)
  def fkCompetitionPerson = foreignKey("fk_competition_person", createdBy, Person)(_.id)

  def * = id ~ name ~ sportId ~ startsOn ~ createdBy ~ createdOn ~ numberOfTeams ~ matchesBetweenTeams ~
    billingTo ~ billingAddress ~ billingAmount
}