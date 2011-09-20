package com.scalablejava.comp.bo

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 6/09/11
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */

class Person(_id: Long, _fname: String, _lname: String, _phone: String, _mobile: String, _email: String,
             _address: String, _username: String, _password: String) {
  def id = _id
  def fname = _fname
  def lname = _lname
  def phone = _phone
  def mobile = _mobile
  def email = _email
  def address = _address
  def username = _username
  def password = _password

}