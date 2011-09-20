package com.scalablejava.utils.database

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 7/09/11
 * Time: 7:41 AM
 * To change this template use File | Settings | File Templates.
 */

class SqlReturn(_pk: Long, _code: Long, _message: String) {
  def pk = {this._pk}
  def code = {this._code}
  def message = {this._message}

}