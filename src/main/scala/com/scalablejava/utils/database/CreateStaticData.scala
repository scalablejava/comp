package com.scalablejava.utils.database

import org.scalaquery.session._
import org.scalaquery.session.Database.threadLocalSession
import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}

import com.scalablejava.comp.datalayer.Sport

/**
 * Created by IntelliJ IDEA.
 * Sport: roberto
 * Date: 6/09/11
 * Time: 10:00 AM
 * To change this template use File | Settings | File Templates.
 */

object CreateStaticData {
  def main(args: Array[String]) {

    val db = Database.forURL("jdbc:mysql://localhost:3306/comp", "root", "161811", null, "com.mysql.jdbc.Driver")
    val nameQuery = for {s <- Sport}
                      yield s.id ~ s.name

    db withSession {
      Sport.name insertAll (
        ("Basketball2"),
        ("Football"),
        ("Rugby Union"),
        ("Rugby League"),
        ("AFL"),
        ("Volleyball"),
        ("Netball"),
        ("Touch-Footy"),
        ("Cricket")
      )
      val q1 = for(s <- Sport) yield s.id ~ s.name

      val list: List[(Long, String)] = nameQuery.list
      for ((id, name) <- list)
          printf("%02d: %s \n", id, name)

    }
  }

}