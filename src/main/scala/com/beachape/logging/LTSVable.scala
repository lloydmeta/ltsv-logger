package com.beachape.logging

import scala.annotation.implicitNotFound

@implicitNotFound("No implicit found for ${A}. Please add or import one.")
trait LTSVable[-A] {
  def toDoubles(o: A): Seq[(String, Any)]
}
