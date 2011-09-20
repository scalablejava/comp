package com.scalablejava.comp.logiclayer

import org.scalaquery.session.Database.threadLocalSession
import org.scalaquery.ql._
import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}

import com.scalablejava.comp.datalayer.Person
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

class PersonService() {

  def get(id: Long): com.scalablejava.comp.bo.Person = {

    val query = for {p <- Person if (p.id === id)}
                yield p.fname ~ p.lname ~ p.phone ~ p.mobile ~ p.email ~ p.address ~ p.username ~ p.password

    var person: com.scalablejava.comp.bo.Person = null

    SQConnection.database withSession {
      person = new com.scalablejava.comp.bo.Person(id, query.first._1, query.first._2, query.first._3, query.first._4,
        query.first._5, query.first._6, query.first._7, query.first._8)
    }
    return person
  }

  def get(username: String, password: String): com.scalablejava.comp.bo.Person = {

    val query = for {p <- Person if (p.username === username && p.password === password)}
                yield p.id ~ p.fname ~ p.lname ~ p.phone ~ p.mobile ~ p.email ~ p.address ~ p.username ~ p.password

    var person: com.scalablejava.comp.bo.Person = null

    SQConnection.database withSession {
      try {
        person = new com.scalablejava.comp.bo.Person(query.first()._1, query.first._2, query.first._3, query.first._4,
          query.first._5, query.first._6, query.first._7, query.first._8, query.first._9)
      } catch {
        case e: java.util.NoSuchElementException => {person = null}
        case e: Exception => e.printStackTrace()
      }

    }
    return person
  }

  def isUsernameOrEmailExist(username: String, email: String): (Boolean, Boolean) = {

    val query = for {p <- Person if (p.username === username || p.email === email)}
                yield p.id ~ p.fname ~ p.lname ~ p.phone ~ p.mobile ~ p.email ~ p.address ~ p.username ~ p.password

    var exists: (Boolean, Boolean) = (true, true)

    SQConnection.database withSession {
      try {
        val person: com.scalablejava.comp.bo.Person  = new com.scalablejava.comp.bo.Person(
          query.first()._1, query.first._2, query.first._3, query.first._4,
          query.first._5, query.first._6, query.first._7, query.first._8, query.first._9)
        exists = (person.username.equals(username), person.email.equals(email))
      } catch {
        case e: java.util.NoSuchElementException => {exists = (false, false)}
        case e: Exception => e.printStackTrace()
      }

    }
    return exists
  }

  def get(): ListBuffer[com.scalablejava.comp.bo.Person] = {

    val list: ListBuffer[com.scalablejava.comp.bo.Person] = ListBuffer[com.scalablejava.comp.bo.Person]()
    val query = for {p <- Person}
                yield p.id ~ p.fname ~ p.lname ~ p.phone ~ p.mobile ~ p.email ~ p.address ~ p.username ~ p.password



    SQConnection.database withSession {
      for (t <- query) {
        val person: com.scalablejava.comp.bo.Person = new com.scalablejava.comp.bo.Person(
          t._1, t._2, t._2, t._4, t._5, t._6, t._7, t._8, t._9)
        list.append(person)
      }
    }
    return list
  }

  /**
   * Returns a tuple (id, errorCode, errorMessage)
   */
  def save(person: com.scalablejava.comp.bo.Person): SqlReturn = {
    var id: Long = person.id
    var result: SqlReturn = null
    try {
      SQConnection.database withSession {

        if (id > 0) {
          val query = for {p <- Person if (p.id is person.id)}
                      yield p.fname ~ p.lname ~ p.phone ~ p.mobile ~ p.email ~ p.address ~ p.username ~ p.password
          query.update(person.fname, person.lname, person.phone, person.mobile, person.email, person.address,
                             person.username, person.password)
        } else {
          Person.fname ~ Person.lname ~ Person.phone ~ Person.mobile ~ Person.email ~ Person.address ~ Person.username ~
            Person.password insert (person.fname, person.lname, person.phone, person.mobile, person.email,
            person.address, person.username, person.password)

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
        var query = Person where {_.id is id}
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