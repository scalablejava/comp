package com.scalablejava.utils.database

import java.sql.SQLException

/**
 * Created by IntelliJ IDEA.
 * Sport: roberto
 * Date: 5/09/11
 * Time: 7:29 AM
 * To change this template use File | Settings | File Templates.
 */


object CreateDatabaseStructure {
  def main(args: Array[String]) {
    if (JdbcConnection.connect("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306", "comp", "root", "161811")) {

      dropDatabaseStructure()
      createDatabaseStructure()

    }
  }

  def createDatabaseStructure() {

    var ddlSql: String = "CREATE TABLE IF NOT EXISTS Sport (" +
    "id INT NOT NULL AUTO_INCREMENT , " +
    "name VARCHAR(45) NULL , " +
    "PRIMARY KEY (id) ) "
    JdbcConnection.getConnetion().prepareStatement(ddlSql).execute()

    ddlSql = "CREATE TABLE IF NOT EXISTS Person (" +
    "id INT NOT NULL AUTO_INCREMENT , " +
    "fname VARCHAR(100) NULL , " +
    "lname VARCHAR(100) NULL , " +
    "phone VARCHAR(45) NULL , "  +
    "mobile VARCHAR(45) NULL , " +
    "email VARCHAR(45) NULL , " +
    "address VARCHAR(255) NULL , " +
    "username VARCHAR(100) NULL , " +
    "password VARCHAR(45) NULL , " +
    "PRIMARY KEY (id), " +
    "UNIQUE KEY unique_username (username) )"
    JdbcConnection.getConnetion().prepareStatement(ddlSql).execute()

    ddlSql = "CREATE TABLE IF NOT EXISTS Team (" +
    "`id` INT NOT NULL AUTO_INCREMENT , " +
    "`name` VARCHAR(100) NULL , " +
    "`createdBy` INT NULL , " +
    "`createdOn` DATETIME NULL , " +
    "PRIMARY KEY (`id`) , " +
    "INDEX `fk_team_person` (`createdBy` ASC) , " +
    "CONSTRAINT `fk_team_person` " +
    "FOREIGN KEY (`createdBy` ) "  +
    "REFERENCES `Person` (`id` ) "  +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION) "
    JdbcConnection.getConnetion().prepareStatement(ddlSql).execute()

    ddlSql = "CREATE TABLE IF NOT EXISTS Competition (" +
    "`id` INT NOT NULL AUTO_INCREMENT , " +
    "`name` VARCHAR(100) NULL , " +
    "`sportId` INT NULL , " +
    "`startsOn` DATETIME NULL , " +
    "`createdBy` INT NULL , " +
    "`createdOn` DATETIME NULL , " +
    "`numberOfTeams` INT NULL , " +
    "`matchesBetweenTeams` INT NULL , " +
    "`billingTo` VARCHAR(255) NULL , " +
    "`billingAddress` VARCHAR(255) NULL , " +
    "`billingAmount` VARCHAR(45) NULL , " +
    "PRIMARY KEY (`id`) , " +
    "INDEX `fk_competition_sport` (`sportId` ASC) , " +
    "INDEX `fk_competition_person` (`createdBy` ASC) , " +
    "CONSTRAINT `fk_competition_sport` " +
    "FOREIGN KEY (`sportId` ) " +
    "REFERENCES `sport` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION, " +
    "CONSTRAINT `fk_competition_person` " +
    "FOREIGN KEY (`createdBy` ) " +
    "REFERENCES `person` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION) "
    JdbcConnection.getConnetion().prepareStatement(ddlSql).execute()

    ddlSql = "CREATE TABLE IF NOT EXISTS Venue (" +
    "`id` INT NOT NULL AUTO_INCREMENT , " +
    "`name` VARCHAR(100) NULL , " +
    "`street` VARCHAR(2) NULL , " +
    "`suburb` VARCHAR(45) NULL , " +
    "`state` VARCHAR(45) NULL , " +
    "`postcode` INT NULL , " +
    "`competitionId` INT NULL , " +
    "PRIMARY KEY (`id`) , " +
    "INDEX `fk_venue_competition` (`competitionId` ASC) , " +
    "CONSTRAINT `fk_venue_competition` " +
    "FOREIGN KEY (`competitionId` ) " +
    "REFERENCES `competition` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION) "
    JdbcConnection.getConnetion().prepareStatement(ddlSql).execute()

    ddlSql = "CREATE  TABLE IF NOT EXISTS VenueAvailable ( " +
    "`id` INT NOT NULL AUTO_INCREMENT , " +
    "`venueId` INT NOT NULL , " +
    "`dayOfWeek` INT NOT NULL COMMENT 'day of the week\n1: Monday\n2: Tuesday\n7: Sunday' , " +
    "`fromHour` INT NOT NULL COMMENT 'Hour format' , " +
    "`fromMin` INT NOT NULL COMMENT 'Hour format' , " +
    "`fromAMPM` TINYINT(1)  NOT NULL , " +
    "`toHour` INT NULL , " +
    "`toMin` INT NULL , " +
    "`toAMPM` TINYINT(1)  NULL , " +
    "`venueAvailableNumber` INT NOT NULL, " +
    "INDEX `fk_venueavailable_venue` (`venueId` ASC) , " +
    "PRIMARY KEY (`id`) , " +
    "CONSTRAINT `fk_venueavailable_venue` " +
    "FOREIGN KEY (`venueId` ) " +
    "REFERENCES `venue` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION) "
    JdbcConnection.getConnetion().prepareStatement(ddlSql).execute()

    ddlSql = "CREATE TABLE IF NOT EXISTS Game (" +
    "`id` INT NOT NULL AUTO_INCREMENT , " +
    "`localTeamId` INT NULL , " +
    "`visitorTeamId` INT NULL , " +
    "`venueId` INT NULL , " +
    "`date` DATETIME NULL , " +
    "`competitionId` INT NULL , " +
    "`localTeamScore` DOUBLE NULL , " +
    "`visitorTeamScore` DOUBLE NULL , " +
    "`fromHour` INT NULL , " +
    "`fromMin` INT NULL , " +
    "`fromAMPM` TINYINT(1)  NULL , " +
    "`toHour` INT NULL , " +
    "`toMin` INT NULL , " +
    "`toAMPM` TINYINT(1)  NULL , " +
    "PRIMARY KEY (`id`) , " +
    "INDEX `fk_game_team1` (`localTeamId` ASC) , " +
    "INDEX `fk_game_team2` (`visitorTeamId` ASC) , " +
    "INDEX `fk_game_competition` (`competitionId` ASC) , " +
    "INDEX `fk_game_venue` (`venueId` ASC) , " +
    "CONSTRAINT `fk_game_team1` " +
    "FOREIGN KEY (`localTeamId` ) " +
    "REFERENCES `team` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION, " +
    "CONSTRAINT `fk_game_team2` " +
    "FOREIGN KEY (`visitorTeamId` ) " +
    "REFERENCES `team` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION, " +
    "CONSTRAINT `fk_game_competition` " +
    "FOREIGN KEY (`competitionId` ) " +
    "REFERENCES `competition` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION, " +
    "CONSTRAINT `fk_game_venue` " +
    "FOREIGN KEY (`venueId` ) " +
    "REFERENCES `venue` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION) "
    JdbcConnection.getConnetion().prepareStatement(ddlSql).execute()

    ddlSql = "CREATE  TABLE IF NOT EXISTS Competing ( " +
    "`teamId` INT NOT NULL , " +
    "`competitionId` INT NOT NULL , " +
    "`joinedBy` INT NULL , " +
    "`joinedOn` DATETIME NULL , " +
    "`teamNumber` INT NULL , " +
    "PRIMARY KEY (`teamId`, `competitionId`) , " +
    "INDEX `fk_competing_team` (`teamId` ASC) , " +
    "INDEX `fk_competing_competition` (`competitionId` ASC) , " +
    "INDEX `fk_competing_joinedBy` (`joinedBy` ASC) , " +
    "CONSTRAINT `fk_competing_team` " +
    "FOREIGN KEY (`teamId` ) " +
    "REFERENCES `team` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION, " +
    "CONSTRAINT `fk_competing_competition` " +
    "FOREIGN KEY (`competitionId` ) " +
    "REFERENCES `competition` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION, " +
    "CONSTRAINT `fk_competing_joinedBy` " +
    "FOREIGN KEY (`joinedBy` ) " +
    "REFERENCES `person` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION) "
    JdbcConnection.getConnetion().prepareStatement(ddlSql).execute()

    ddlSql = "CREATE  TABLE IF NOT EXISTS Player ( " +
    "`teamId` INT NOT NULL , " +
    "`personId` INT NOT NULL , " +
    "PRIMARY KEY (`teamId`, `personId`) , " +
    "INDEX `fk_player_team` (`teamId` ASC) , " +
    "INDEX `fk_player_person` (`personId` ASC) , " +
    "CONSTRAINT `fk_player_team` " +
    "FOREIGN KEY (`teamId` ) " +
    "REFERENCES `team` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION, " +
    "CONSTRAINT `fk_player_person` " +
    "FOREIGN KEY (`personId` ) " +
    "REFERENCES `person` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION) "
    JdbcConnection.getConnetion().prepareStatement(ddlSql).execute()

    ddlSql = "CREATE TABLE IF NOT EXISTS MatchSettings (" +
    "`id` INT NOT NULL AUTO_INCREMENT , " +
    "`venueId` INT NOT NULL , " +
    "`dayOftheWeek` INT NOT NULL, " +
    "`startTime` TIME NOT NULL, " +
    "`endTime` TIME NOT NULL, " +
    "`competitionId` INT NOT NULL, " +
    "PRIMARY KEY (`id`) , " +
    "INDEX `fk_matchsettings_1` (`venueId` ASC) , " +
    "INDEX `fk_matchsettings_2` (`competitionId` ASC) , " +
    "CONSTRAINT `fk_matchsettings_1` " +
    "FOREIGN KEY (`venueId` ) " +
    "REFERENCES `venue` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION, " +
    "CONSTRAINT `fk_matchsettings_2` " +
    "FOREIGN KEY (`competitionId` ) " +
    "REFERENCES `competition` (`id` ) " +
    "ON DELETE NO ACTION " +
    "ON UPDATE NO ACTION) "
    JdbcConnection.getConnetion().prepareStatement(ddlSql).execute()

  }

  def dropDatabaseStructure() {
    dropTable("MatchSettings")
    dropTable("Player")
    dropTable("Competing")
    dropTable("Game")
    dropTable("VenueAvailable")
    dropTable("Venue")
    dropTable("Competition")
    dropTable("Team")
    dropTable("Sport")
    dropTable("Person")
  }

  def dropTable(table: String) {
    var ddlSql: String = ""

    try {
      var ddlSql = "DROP TABLE " + table
      JdbcConnection.getConnetion().prepareStatement(ddlSql).execute()
    } catch {
      case e: SQLException => println("Table " + table + " Could Not be Dropped")
      case e: Exception => e.printStackTrace()
    }

  }

  def insertStaticData() {
    var sql: String = ""

  }
}