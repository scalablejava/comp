package com.scalablejava.utils.database

import java.sql._
/**
 * Created by IntelliJ IDEA.
 * Sport: roberto
 * Date: 5/09/11
 * Time: 7:04 AM
 * To change this template use File | Settings | File Templates.
 */

object JdbcConnection {
  var conn: java.sql.Connection = null

  def connect(driver: String, server: String, database: String, username: String
              , password: String) : Boolean = {

    var isConnected: Boolean = false

    try {
      Class.forName(driver).newInstance()
      conn = DriverManager.getConnection(server + "/" + database, username, password);
      isConnected = true
    }
    return isConnected
  }

  def getConnetion() : java.sql.Connection = {
    return conn
  }

}