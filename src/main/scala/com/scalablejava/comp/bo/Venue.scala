package com.scalablejava.comp.bo

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 11/09/11
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */

class Venue(_id: Long, _name: String, _street: String, _suburb: String, _state: String, _postcode: Int
            , _competitionId: Long) {
  def id = _id
  def name = _name
  def street = _street
  def suburb = _suburb
  def state = _state
  def postcode = _postcode
  def competitionId = _competitionId
}