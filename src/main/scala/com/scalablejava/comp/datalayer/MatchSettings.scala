package com.scalablejava.comp.datalayer

import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}
import java.sql.Time

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 15/09/11
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */

object MatchSettings extends Table[(Long, Long, Int, Time, Time, Long)]("MatchSettings") {
  def id = column[Long]("id", O NotNull, O PrimaryKey, O AutoInc)
  def venueId = column[Long]("venueId", O NotNull)
  def dayOfTheWeek = column[Int]("dayOfTheWeek", O NotNull)
  def startTime = column[Time]("startTime", O NotNull)
  def endTime = column[Time]("endTime", O NotNull)
  def competitionId = column[Long]("competitionId", O NotNull)

  def * = id ~ venueId ~ dayOfTheWeek ~ startTime ~ endTime ~ competitionId
}