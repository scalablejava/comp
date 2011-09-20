package com.scalablejava.comp.datalayer

import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}


/**
 * Created by IntelliJ IDEA.
 * Sport: roberto
 * Date: 6/09/11
 * Time: 6:41 AM
 * To change this template use File | Settings | File Templates.
 */

object Venue extends Table[(Long, String, String, String, String, Int, Long)]("Venue") {
  def id = column[Long]("id", O NotNull, O PrimaryKey, O AutoInc)
  def name = column[String]("name")
  def street = column[String]("street")
  def suburb = column[String]("suburb")
  def state = column[String]("state")
  def postcode = column[Int]("postcode")
  def competitionId = column[Long]("competitionId")

  def fkVenueCompetition = foreignKey("fk_venue_competition", competitionId, Competition)(_.id)

  def * = id ~ name ~ street ~ suburb ~ state ~ postcode ~ competitionId


}