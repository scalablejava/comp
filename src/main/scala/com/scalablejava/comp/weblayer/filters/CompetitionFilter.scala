package com.scalablejava.comp.weblayer.filters

import com.scalablejava.utils.Misc
import com.scalablejava.comp.logiclayer.{VenueService, CompetitionService, SportService}
import com.scalablejava.comp.bo.MatchSetting
import java.sql.Time

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 12/09/11
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */

class CompetitionFilter extends CompetitionFilterHelper {

  var values: (String, Long, Int, Int) = ("", -1, -1, -1)

  get("/competition/create/step1") {

    var sportOptions = ""
    var numberOfTeamsOptions = ""
    var matchesBetweenTeamsOptions = ""
    val service: SportService = new SportService
    val list = service.get()
    list.foreach(s => {
      sportOptions += "<option value='" + s.id + "'>" + s.name + "</option>"
    })


    for(i <- 1 to  12) {
      numberOfTeamsOptions += "<option value='" + i + "'>" + i + "</option>"
    }

    for(i <- 1 to  4) {
      matchesBetweenTeamsOptions += "<option value='" + i + "'>" + i + "</option>"
    }

    templateEngine.layout("/WEB-INF/scalate/templates/competition_s1.ssp",
      Map(
        "validationErrors" -> validationErrors,
        "sportOptions" -> sportOptions,
        "numberOfTeamsOptions" -> numberOfTeamsOptions,
        "matchesBetweenTeamsOptions" -> matchesBetweenTeamsOptions,
        "values" -> values
      )
    )


  }


  post("/competition/create/step2") {
    var nextUrl: String = "/competition/create/step1"

    val competitionId: Int = -1
    val competitionName: String = params("competitionName")
    val sportId: Int = params("sport").toInt
    val startsOn: java.sql.Date = new java.sql.Date(new java.util.Date().getTime)
    val createdBy: Int = 1
    val createdOn: java.sql.Date = new java.sql.Date(new java.util.Date().getTime)
    val numberOfTeams: Int = params("numberOfTeams").toInt
    val matchesBetweenTeams: Int = params("matchesBetweenTeams").toInt
    val billingTo: String = ""
    val billingAddress: String = ""
    val billingAmount: Double = 0.0


    values = (competitionName, sportId, numberOfTeams, matchesBetweenTeams)

    if (validate(competitionName, sportId, numberOfTeams, matchesBetweenTeams)) {
      val competition = new com.scalablejava.comp.bo.Competition(competitionId, competitionName, sportId, startsOn,
        createdBy, createdOn, numberOfTeams, matchesBetweenTeams, billingTo, billingAddress, billingAmount)
      val service: CompetitionService = new CompetitionService
      val sqlReturn = service.save(competition)
      if (sqlReturn.code == -1) {
        nextUrl = "/competition/create/step2"

        session("competitionId") = sqlReturn.pk.toString
        session("numberOfTeams") = numberOfTeams.toString
        session("matchesBetweenTeams") = matchesBetweenTeams.toString

      } else {
        validationErrors += Misc.addSqlErrorMessage(sqlReturn)
      }

    }
    redirect(nextUrl)

  }


  get("/competition/create/step2") {

    val competitionId = session("competitionId").toString.toLong
    session("numberOfTeams") = session("numberOfTeams")
    session("matchesBetweenTeams") = session("matchesBetweenTeams")

    var venueList = ""
    val list = venues(competitionId)
    list.foreach(s => {
      venueList += "<tr><td>" + s.id + "</td>"
      venueList += "<td>" + s.name + "</td>"
        venueList += "<td>" + s.street + " " + s.suburb + " " +  s.state + " " +  s.postcode + "</td>"
        venueList += "<td><a href='/competition/venue/remove/" + s.id + "'>Remove</a></td></tr>"
    })

    templateEngine.layout("/WEB-INF/scalate/templates/competition_s2.ssp",
      Map(
        "validationErrors" -> validationErrors,
        "venueList" -> venueList,
        "values" -> values
      )
    )

  }


  post("/competition/create/venue/create") {

    if (validateVenue(params("name"), params("postcode"))) {
      val name = params("name")
      val street = params("street")
      val suburb = params("suburb")
      val state = params("state")
      val postcode = params("postcode").toInt

      val competitionId = session("competitionId").toString.toLong
      val service: VenueService = new VenueService

      val venue = new com.scalablejava.comp.bo.Venue(-1, name, street, suburb, state, postcode, competitionId)
      val sqlReturn: com.scalablejava.utils.database.SqlReturn = service.save(venue)
      if (sqlReturn.code != -1) {
        validationErrors += Misc.addSqlErrorMessage(sqlReturn)
      }
    }
    redirect("/competition/create/step2")
  }


  get("/competition/create/step3") {
    val competitionId = session("competitionId").toString.toLong
    val numberOfTeams = session("numberOfTeams").toString.toInt
    val matchesBetweenTeams = session("matchesBetweenTeams").toString.toInt

    templateEngine.layout("/WEB-INF/scalate/templates/competition_s3.ssp",
      Map(
        "validationErrors" -> validationErrors,
        "settingPerMatchHtml" -> settingPerMatchHtml(competitionId, numberOfTeams,
          matchesBetweenTeams)
      )
    )

  }

  post("/competition/create/step3") {
    redirect("/competition/create/step3")
  }

  get("/competition/create/step4") {
    templateEngine.layout("/WEB-INF/scalate/templates/competition_s4.ssp")
  }

  post("/competition/create/step4") {
    var nextUrl = "/competition/create/step3"
    val competitionId = session("competitionId").toString.toLong
    val numberOfTeams = session("numberOfTeams").toString.toInt

    println(numberOfTeams)
    val service = new CompetitionService
    for (i<-1 to numberOfTeams/2) {
      val matchSettings = new MatchSetting(-1, params("venue" + i).toInt, params("day" + i).toInt,
      Time.valueOf(params("startTime" + i)), Time.valueOf(params("endTime" + i)), competitionId )
      val sqlReturn = service.saveMatchSettings(matchSettings)
      if (sqlReturn.code == -1) {
        nextUrl = "/competition/create/step4"
      } else {
        validationErrors += Misc.addSqlErrorMessage(sqlReturn)
      }

    }
    redirect(nextUrl)
  }

  get("/competition/create/laststep") {
    redirect("/home")
  }

  post("/competition/create/laststep") {
    redirect("/competition/create/laststep")
  }

  //Agregado por Lissett
    get("/competition/competitionId") {
    templateEngine.layout("/WEB-INF/scalate/templates/competitionId2.ssp")
  }
    get("/competition/teamId") {
    templateEngine.layout("/WEB-INF/scalate/templates/teamId.ssp")
  }
    get("/competition/competitions") {
    templateEngine.layout("/WEB-INF/scalate/templates/competition.ssp")
  }
    get("/competition/teams") {
    templateEngine.layout("/WEB-INF/scalate/templates/team.ssp")
  }
    get("/competition/creteteam") {
    templateEngine.layout("/WEB-INF/scalate/templates/createteam.ssp")
  }
    get("/competition/test") {
    templateEngine.layout("/WEB-INF/scalate/templates/createteam.ssp")
  }
}