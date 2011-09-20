package com.scalablejava.comp.logiclayer

import com.scalablejava.utils.database.SQConnection
import com.scalablejava.comp.datalayer.Sport

import org.scalaquery.session.Database.threadLocalSession
import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}

import com.scalablejava.utils.database.{SqlReturn, SQConnection}
import collection.mutable.ListBuffer


/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 13/09/11
 * Time: 7:28 AM
 * To change this template use File | Settings | File Templates.
 */

class SportService {

  def get(): ListBuffer[com.scalablejava.comp.bo.Sport] = {

    val list: ListBuffer[com.scalablejava.comp.bo.Sport] = ListBuffer[com.scalablejava.comp.bo.Sport]()
    val query = for {s <- Sport}
                yield s.id ~ s.name


    SQConnection.database withSession {
      for (t <- query) {
        val sport: com.scalablejava.comp.bo.Sport = new com.scalablejava.comp.bo.Sport(
          t._1, t._2)
        list.append(sport)
      }
    }
    return list
  }

}