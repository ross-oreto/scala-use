package io.oreto.str

object Strings {

  object Chars {
    val NEGATIVE = '-'
    val DECIMAL = '.'
  }

  implicit class Str(val s: String) {

    def isNumber: Boolean = {
      // do some initial sanity checking to catch easy issues quickly and with very little processing
      // also this makes the state tracking in the case statements
      var dotted = false
      !(s.isBlank
        || (s.length == 1 && !s.charAt(0).isDigit)
        || s.endsWith(String.valueOf(Chars.DECIMAL))) &&
        s.zipWithIndex.forall(c => {
        c._1 match {
          // negative can only be in the start position
          case Chars.NEGATIVE if c._2 == 0 => true
          case Chars.NEGATIVE => false
          // only one decimal place allowed
          case Chars.DECIMAL if dotted => false
          case Chars.DECIMAL => dotted = true; true
          // this better be a digit
          case _ if c._1.isDigit => true
          case _ => false
        }
      })
    }
  }
}