package com.scalablejava.comp.weblayer.filters

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 10/09/11
 * Time: 7:13 PM
 * To change this template use File | Settings | File Templates.
 */

class HomeFilter extends CompBaseFilter {

  var values = ("", "")

  get("/home") {
    templateEngine.layout("/WEB-INF/scalate/templates/home.ssp",
      Map("title"->"Home",
        "values"->values))



  }

}