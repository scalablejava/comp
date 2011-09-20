package com.scalablejava.utils.database

import org.scalaquery.session.Database

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 6/09/11
 * Time: 6:29 PM
 * To change this template use File | Settings | File Templates.
 */

object SQConnection {

  def database = {Database.forURL("jdbc:mysql://localhost:3306/comp", "root", "161811", null, "com.mysql.jdbc.Driver")}

}