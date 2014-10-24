package com.beachape.logging

import scala.annotation.implicitNotFound

@implicitNotFound("No implicit found for ${A}. Please add or import one.")
trait LTSVable[-A] {

  /**
   * Turns an [[A]] into Seq[(String, Any)]
   */
  def toPairs(o: A): Seq[(String, Any)]
}
