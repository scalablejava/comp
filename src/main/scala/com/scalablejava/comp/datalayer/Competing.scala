package com.scalablejava.comp.datalayer

import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}
import java.sql.Date

/**
 * Created by IntelliJ IDEA.
 * Sport: roberto
 * Date: 6/09/11
 * Time: 7:24 AM
 * To change this template use File | Settings | File Templates.
 */

object Competing extends Table[(Long, Long, Long, Date, Int)]("Competing") {
  def teamId = column[Long]("teamId", O NotNull)
  def competitionId = column[Long]("competitionId", O NotNull)
  def joinedBy = column[Long]("joinedBy", O NotNull)
  def joinedOn = column[Date]("joinedOn", O NotNull)
  def teamNumber = column[Int]("teamNumber", O NotNull)

  def pk = primaryKey("pk_competing", teamId ~ competitionId)
  def fkCompetingTeam = foreignKey("fk_competing_team", teamId, Team)(_.id)
  def fkCompetingCompetition = foreignKey("fk_competing_competition", competitionId, Competition)(_.id)
  def fkCompetingJoinedBy = foreignKey("fk_competing_joinedBy", joinedBy, Person)(_.id)

  def * = teamId ~ competitionId ~ joinedBy ~ joinedOn ~ teamNumber
}