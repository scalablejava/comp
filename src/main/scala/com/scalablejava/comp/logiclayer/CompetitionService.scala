package com.scalablejava.comp.logiclayer

import org.scalaquery.session.Database.threadLocalSession
import org.scalaquery.ql._
import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}

import java.sql.SQLException
import com.scalablejava.utils.database.{SqlReturn, SQConnection}
import com.scalablejava.comp.datalayer._

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 13/09/11
 * Time: 6:45 AM
 * To change this template use File | Settings | File Templates.
 */

class CompetitionService {

  def save(competition: com.scalablejava.comp.bo.Competition): SqlReturn = {
    var id: Long = competition.id
    var result: SqlReturn = null
    try {
      SQConnection.database withSession {

        if (id > 0) {
          val query = for {c <- com.scalablejava.comp.datalayer.Competition if (c.id is competition.id)}
                      yield c.name ~ c.sportId ~ c.startsOn ~ c.createdBy ~ c.createdOn ~ c.numberOfTeams ~
                        c.matchesBetweenTeams ~ c.billingTo ~ c.billingAddress ~ c.billingAmount
          query.update(competition.name, competition.sportId, competition.startsOn, competition.createdBy,
            competition.createdOn, competition.numberOfTeams, competition.matchesBetweenTeams,
            competition.billingTo, competition.billingAddress, competition.billingAmount)
        } else {
          Competition.name ~ Competition.sportId ~ Competition.startsOn ~ Competition.createdBy ~
            Competition.createdOn ~ Competition.numberOfTeams ~ Competition.matchesBetweenTeams ~
            Competition.billingTo ~ Competition.billingAddress ~ Competition.billingAmount insert (competition.name,
            competition.sportId, competition.startsOn, competition.createdBy,
            competition.createdOn, competition.numberOfTeams, competition.matchesBetweenTeams,
            competition.billingTo, competition.billingAddress, competition.billingAmount)

          val idQuery = SimpleFunction.nullary[Long]("LAST_INSERT_ID")
          id = Query(idQuery).first()

        }
      }
      result = new SqlReturn(id, -1, "")

    } catch {
      case e: SQLException => {
        result = new SqlReturn(-1, e.getErrorCode, e.getMessage)
      }
      case e: Exception => {
        result = null
        e.printStackTrace()
      }
    }
    return result
  }

  def saveMatchSettings(setting: com.scalablejava.comp.bo.MatchSetting): SqlReturn = {
    var id: Long = setting.id
    var result: SqlReturn = null
    try {
      SQConnection.database withSession {

        if (id > 0) {
          val query = for {c <- com.scalablejava.comp.datalayer.MatchSettings if (c.id is setting.id)}
                      yield c.venueId ~ c.dayOfTheWeek ~ c.startTime ~ c.endTime ~ c.competitionId
          query.update(setting.venueId, setting.dayOfTheWeek, setting.startTime, setting.endTime, setting.competitionId)
        } else {
          MatchSettings.venueId ~ MatchSettings.dayOfTheWeek ~ MatchSettings.startTime ~ MatchSettings.endTime ~
            MatchSettings.competitionId insert (setting.venueId, setting.dayOfTheWeek, setting.startTime,
            setting.endTime, setting.competitionId)
          val idQuery = SimpleFunction.nullary[Long]("LAST_INSERT_ID")
          id = Query(idQuery).first()

        }
      }
      result = new SqlReturn(id, -1, "")

    } catch {
      case e: SQLException => {
        result = new SqlReturn(-1, e.getErrorCode, e.getMessage)
      }
      case e: Exception => {
        result = null
        e.printStackTrace()
      }
    }
    return result

  }


}