package com.scalablejava.utils

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 10/09/11
 * Time: 10:10 PM
 * To change this template use File | Settings | File Templates.
 */

object Security {

  def computeHash(text: String): Array[Byte] = {
    val messageDigest: java.security.MessageDigest = java.security.MessageDigest.getInstance("SHA-1")
    messageDigest.reset
    messageDigest.update(text.getBytes())
    return messageDigest.digest

  }

  def byteArrayToHexString(b: Array[Byte]): String = {
    var sb: StringBuilder = new StringBuilder(b.length)
    var v: Int = 0
    b.foreach((bt: Byte) => {
      v = (bt & 0xff)
      if (v < 16) {
        sb.append('0')
      }
      sb.append(Integer.toHexString(v))
    })
    return sb.toString.toUpperCase
  }

}