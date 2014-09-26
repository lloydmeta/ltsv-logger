package com.beachape.logging

import scala.reflect.macros._

private[logging] object LTSVLogWriterMacros {

  type LoggerContext = Context { type PrefixType = LTSVLoggerLike }

  /* Info */
  def infoImpl(c: LoggerContext)(pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("info", None, pairs: _*)

  def infoErrImpl(c: LoggerContext)(error: c.Expr[Throwable], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("info", Some(error), pairs: _*)

  /* Debug */
  def debugImpl(c: LoggerContext)(pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("debug", None, pairs: _*)

  def debugErrImpl(c: LoggerContext)(error: c.Expr[Throwable], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("debug", Some(error), pairs: _*)

  /* Warn */
  def warnImpl(c: LoggerContext)(pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("warn", None, pairs: _*)

  def warnErrImpl(c: LoggerContext)(error: c.Expr[Throwable], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("warn", Some(error), pairs: _*)

  /* Error */
  def errorImpl(c: LoggerContext)(pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("error", None, pairs: _*)

  def errorErrImpl(c: LoggerContext)(error: c.Expr[Throwable], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("error", Some(error), pairs: _*)

  /* Trace */
  def traceImpl(c: LoggerContext)(pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("trace", None, pairs: _*)

  def traceErrImpl(c: LoggerContext)(error: c.Expr[Throwable], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("trace", Some(error), pairs: _*)

  private def ltsvErrLogAtLevelIfEnabled(c: LoggerContext)(level: String, error: Option[c.Expr[Throwable]], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] = {
    import c.universe._
    val isLevelEnabled = newTermName(s"is${level.toLowerCase.capitalize}Enabled")
    val logLevel = newTermName(level.toLowerCase)
    val writer = c.prefix.tree
    val tree = error match {
      case Some(err) => q"if (${writer}.underlying.$isLevelEnabled) $writer.underlying.$logLevel($writer.toLtsv(..$pairs), $err)"
      case None => q"if (${writer}.underlying.$isLevelEnabled) $writer.underlying.$logLevel($writer.toLtsv(..$pairs))"
    }
    c.Expr[Unit](tree)
  }

}
