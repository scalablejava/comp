package com.scalablejava.comp.datalayer

import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}
import java.sql.Date

/**
 * Created by IntelliJ IDEA.
 * Sport: roberto
 * Date: 6/09/11
 * Time: 7:07 AM
 * To change this template use File | Settings | File Templates.
 */

object Game extends Table[(Long, Long, Long, Long, Date, Long, Double, Double, Int, Int, Int, Int, Int, Int)]("Game") {
  def id = column[Long]("id", O NotNull, O PrimaryKey)
  def localTeamId = column[Long]("localTeamId", O NotNull)
  def visitorTeamId = column[Long]("visitorTeamId", O NotNull)
  def venueId = column[Long]("venueId", O NotNull)
  def date = column[Date]("date", O NotNull)
  def competitionId = column[Long]("competitionId", O NotNull)
  def localTeamScore = column[Double]("localTeamScore")
  def visitorTeamScore = column[Double]("visitorTeamScore")
  def fromHour = column[Int]("fromHour", O NotNull)
  def fromMin = column[Int]("fromMin", O NotNull)
  def fromAMPM = column[Int]("fromAMPM", O NotNull)
  def toHour= column[Int]("toHour", O NotNull)
  def toMin = column[Int]("toMin", O NotNull)
  def toAMPM = column[Int]("toAMPM", O NotNull)

  def fkGameLocalTeam = foreignKey("fk_game_team1", localTeamId, Team)(_.id)
  def fkGameVisitorTeam = foreignKey("fk_game_team2", visitorTeamId, Team)(_.id)
  def fkGameCompetition = foreignKey("fk_game_competition", competitionId, Competition)(_.id)
  def fkGameVenue = foreignKey("fk_game_venue", venueId, Venue)(_.id)

  def * = id ~ localTeamId ~ visitorTeamId ~ venueId ~ date ~ competitionId ~ localTeamScore ~ visitorTeamScore ~
          fromHour ~ fromMin ~ fromAMPM ~ toHour ~ toMin ~ toAMPM

}