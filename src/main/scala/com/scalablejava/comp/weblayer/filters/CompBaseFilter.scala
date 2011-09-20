package com.scalablejava.comp.weblayer.filters

import org.scalatra.ScalatraFilter
import org.scalatra.scalate.ScalateSupport

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 11/09/11
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */

class CompBaseFilter extends ScalatraFilter with ScalateSupport {
  var validationErrors: String = ""

  protected def contextPath = request.getContextPath

  before() {
    contentType = "text/html"
  }

}