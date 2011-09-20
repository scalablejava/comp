package com.scalablejava.comp.logiclayer

import org.scalaquery.session.Database.threadLocalSession
import org.scalaquery.ql._
import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}

import com.scalablejava.comp.datalayer.Venue
import java.sql.SQLException
import com.scalablejava.utils.database.{SqlReturn, SQConnection}
import collection.mutable.ListBuffer

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 6/09/11
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */

class VenueService() {

  def get(id: Long): com.scalablejava.comp.bo.Venue = {

    val query = for {p <- Venue if (p.id === id)}
                yield p.name ~ p.street ~ p.suburb ~ p.state ~ p.postcode ~ p.competitionId

    var venue: com.scalablejava.comp.bo.Venue = null

    SQConnection.database withSession {
      venue = new com.scalablejava.comp.bo.Venue(id, query.first._1, query.first._2, query.first._3, query.first._4,
        query.first._5, query.first._6)
    }
    return venue
  }

  def get(): ListBuffer[com.scalablejava.comp.bo.Venue] = {

    val list: ListBuffer[com.scalablejava.comp.bo.Venue] = ListBuffer[com.scalablejava.comp.bo.Venue]()
    val query = for {p <- Venue}
                yield p.id ~ p.name ~ p.street ~ p.suburb ~ p.state ~ p.postcode ~ p.competitionId

    SQConnection.database withSession {
      for (t <- query) {
        val person: com.scalablejava.comp.bo.Venue = new com.scalablejava.comp.bo.Venue (
          t._1, t._2, t._3, t._4, t._5, t._6, t._7)
        list.append(person)
      }
    }
    return list
  }

  def getByCompetitionId(competitionId: Long): List[com.scalablejava.comp.bo.Venue] = {

    val list: ListBuffer[com.scalablejava.comp.bo.Venue] = ListBuffer[com.scalablejava.comp.bo.Venue]()
    val query = for {p <- Venue if p.competitionId is competitionId}
                yield p.id ~ p.name ~ p.street ~ p.suburb ~ p.state ~ p.postcode ~ p.competitionId

    SQConnection.database withSession {
      println("Venue: SQConnection.database withSession: " + query.selectStatement)

      for (t <- query) {
        val venue: com.scalablejava.comp.bo.Venue = new com.scalablejava.comp.bo.Venue (
          t._1, t._2, t._3, t._4, t._5, t._6, t._7)
          println("Venue: " + t._1)
        list.append(venue)
      }
    }
    return list.toList
  }

  /**
   * Returns a tuple (id, errorCode, errorMessage)
   */
  def save(venue: com.scalablejava.comp.bo.Venue): SqlReturn = {
    var id: Long = venue.id
    var result: SqlReturn = null
    try {
      SQConnection.database withSession {

        if (id > 0) {
          val query = for {p <- com.scalablejava.comp.datalayer.Venue if (p.id is venue.id)}
                      yield p.name ~ p.street ~ p.suburb ~ p.state ~ p.postcode ~ p.competitionId
//          query.update(venue.name ~ venue.street ~ venue.suburb ~ venue.state ~ venue.postcode ~ venue.competitionId)
        } else {
          Venue.name ~ Venue.street ~ Venue.suburb ~ Venue.state ~ Venue.postcode ~ Venue.competitionId insert (
            venue.name ~ venue.street ~ venue.suburb ~ venue.state ~ venue.postcode ~ venue.competitionId)

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

  def delete(id: Long): SqlReturn = {
    var result: SqlReturn = null
    try {
      SQConnection.database withSession {
        var query = Venue where {_.id is id}
        query.delete
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