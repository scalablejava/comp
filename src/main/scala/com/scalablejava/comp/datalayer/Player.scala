package com.scalablejava.comp.datalayer

import org.scalaquery.ql.extended.MySQLDriver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}

/**
 * Created by IntelliJ IDEA.
 * Sport: roberto
 * Date: 6/09/11
 * Time: 7:35 AM
 * To change this template use File | Settings | File Templates.
 */

object Player extends Table[(Long, Long)]("Player") {
  def teamId = column[Long]("teamId", O NotNull)
  def personId = column[Long]("personId", O NotNull)

  def pk = primaryKey("pk_player", teamId ~ personId)
  def fkPlayerTeam = foreignKey("fk_player_team", teamId, Team)(_.id)
  def fkPlayerPerson = foreignKey("fk_player_person", personId, Person)(_.id)

  def * = teamId ~ personId

}