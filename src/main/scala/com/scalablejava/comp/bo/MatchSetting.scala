package com.scalablejava.comp.bo

import java.sql.Time

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 15/09/11
 * Time: 9:22 PM
 * To change this template use File | Settings | File Templates.
 */

class MatchSetting(_id: Long, _venueId: Long, _dayOfTheWeek: Int, _startTime: Time, _endTime: Time
                   , _competitionId: Long) {
  def id = _id
  def venueId = _venueId
  def dayOfTheWeek = _dayOfTheWeek
  def startTime = _startTime
  def endTime = _endTime
  def competitionId = _competitionId

}