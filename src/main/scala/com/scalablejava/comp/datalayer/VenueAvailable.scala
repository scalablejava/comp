package com.scalablejava.comp.datalayer

import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}

/**
 * Created by IntelliJ IDEA.
 * Sport: roberto
 * Date: 6/09/11
 * Time: 6:47 AM
 * To change this template use File | Settings | File Templates.
 */

object VenueAvailable extends Table[(Long, Long, Int, Int, Int, Int, Int, Int, Int)]("VenueAvailable") {
  def id = column[Long]("id", O NotNull, O PrimaryKey)
  def venueId = column[Long]("venueId", O NotNull)
  def dayOfWeek = column[Int]("dayOfWeek", O NotNull)
  def fromHour = column[Int]("fromHour", O NotNull)
  def fromMin = column[Int]("fromMin", O NotNull)
  def fromAMPM = column[Int]("fromAMPM", O NotNull)
  def toHour= column[Int]("toHour", O NotNull)
  def toMin = column[Int]("toMin", O NotNull)
  def toAMPM = column[Int]("toAMPM", O NotNull)

  def fkVenueAvailableVenue = foreignKey("fk_venueavailable_venue", venueId, Venue)(_.id)

  def * = id ~ venueId ~ dayOfWeek ~ fromHour ~ fromMin ~ fromAMPM ~ toHour ~ toMin ~ toAMPM
}