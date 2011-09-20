package com.scalablejava.comp.weblayer.filters

import collection.mutable.ArrayBuffer
import com.scalablejava.comp.logiclayer.VenueService
import com.scalablejava.utils.Misc

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 15/09/11
 * Time: 7:32 PM
 * To change this template use File | Settings | File Templates.
 */

class CompetitionFilterHelper extends CompBaseFilter {
  def validate(competitionName: String, sport: Int, numberOfteams: Int, matchesBetweenTeams: Int): Boolean = {

    validationErrors = ""
    var validations = new ArrayBuffer[String]()
    if (competitionName.equals("")) {
      validations.append("Please enter a name for the competition")
    }
    if (sport <= 0) {
      validations.append("Please select the sport for the competition.")
    }
    if (numberOfteams <= 0) {
      validations.append("Please select how many teams will play in the competition.")
    }
    if (matchesBetweenTeams <= 0) {
      validations.append("Please enter how many matches teams will play against each other team")
    }

    validations.foreach((message: String) => {
      validationErrors += "<li>" + message + "</li>"
    })

    return validationErrors.equals("")
  }

  def venues(competitionId: Long): List[com.scalablejava.comp.bo.Venue] = {
    val service: VenueService = new VenueService
    return service.getByCompetitionId(competitionId)
  }

  def validateVenue(name: String, postcode: String): Boolean = {
    validationErrors = ""
    var validations = new ArrayBuffer[String]()
    if (name.trim.equals("")) {
      validations.append("Please enter the venue name")
    }
    try {
      val number = postcode.toInt
    } catch {
      case e: Exception => {validations.append("Please enter a numeric postcode")}
    }
    validations.foreach((message: String) => {
      validationErrors += "<li>" + message + "</li>"
    })

    return validationErrors.equals("")
  }

  def comboDays(number: Int): String = {
    var html = ""
    html += "<select name='day" + number + "' id='day" + number + "'>"
    html += "  <option value='-1'>Select Day</option>"
    Misc.daysOfTheWeek.foreach(d => {
      html += "<option value='" + d._1 + "'>" + d._2 + "</option>"
    })
    html += "</select>"
    return html
  }

  def comboVenue(competitionId: Long, number: Int): String = {
    var html = ""
    html += "<select name='venue" + number + "' id='venue" + number + "'>"
    html += "  <option value='-1'>Select Venue</option>"
    venues(competitionId).foreach(v => {
      html += "<option value='" + v.id + "'>" + v.name + "</option>"
    })
    html += "</select>"
    return html
  }

  def comboAMPM(from: Boolean, number: Int): String = {
    val name = (if (from) "from" else "to") + "AmPm" + number
    var html = ""
    html += "<select name='" + name + "' id='" + name + "'>"
    html += "  <option value='-1'>Select AM/PM</option>"
    html += "  <option value='1'>AM</option>"
    html += "  <option value='2'>PM</option>"
    return html
  }

  def settingPerMatchHtml(competitionId: Long, numberOfTeams: Int, matchesBetweenTeams: Int): String = {
    var html = ""

    for (i<-1 to (numberOfTeams/2)) {
      html += "<tr>"
      html += "<td>" + i + "</td>"
      html += "<td>" + comboVenue(competitionId, i) + "</td>"
      html += "<td>" + comboDays(i) + "</td>"
      html += "<td><input type='text' name='startTime" + i + "' id='startTime" + i + "'/></td>"
      html += "<td><input type='text' name='endTime" + i + "' id='endTime" + i + "'/></td>"
      html += "</tr>"
    }
    return html
  }

}