package com.scalablejava.comp.bo

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 13/09/11
 * Time: 6:47 AM
 * To change this template use File | Settings | File Templates.
 */

class Competition(_id: Long, _name: String, _sportId: Long, _startsOn: java.sql.Date, _createdBy: Long,
                   _createdOn: java.sql.Date, _numberOfTeams: Int, _matchesBetweenTeams: Int,
                   _billingTo: String, _billingAddress: String, _billingAmount: Double) {
  def id = _id
  def name = _name
  def sportId = _sportId
  def startsOn = _startsOn
  def createdBy = _createdBy
  def createdOn = _createdOn
  def numberOfTeams = _numberOfTeams
  def matchesBetweenTeams = _matchesBetweenTeams
  def billingTo = _billingTo
  def billingAddress = _billingAddress
  def billingAmount = _billingAmount

}