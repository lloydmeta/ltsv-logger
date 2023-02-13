package com.beachape.logging

import scala.reflect.macros._

private[logging] object LTSVLogWriterMacros {

  type LoggerContext = whitebox.Context { type PrefixType = LTSVLoggerLike }

  /* Info */
  def infoImpl(c: LoggerContext)(pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("info", None, pairs: _*)

  def infoErrImpl(c: LoggerContext)(error: c.Expr[Throwable], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("info", Some(error), pairs: _*)

  def infoMsgImpl(c: LoggerContext)(message: c.Expr[String]): c.Expr[Unit] =
    ltsvErrMsgLogAtLevelIfEnabled(c)("info", None, message)

  def infoGenImpl[A: c.WeakTypeTag](c: LoggerContext)(obj: c.Expr[A], pairs: c.Expr[(String, Any)]*)(ltsvable: c.Expr[LTSVable[A]]): c.Expr[Unit] = {
    ltsvErrMsgLogAtLevelIfEnabledGen(c)("info", None, obj, ltsvable, pairs: _*)
  }

  def infoGenErrImpl[A: c.WeakTypeTag](c: LoggerContext)(error: c.Expr[Throwable], obj: c.Expr[A], pairs: c.Expr[(String, Any)]*)(ltsvable: c.Expr[LTSVable[A]]): c.Expr[Unit] = {
    ltsvErrMsgLogAtLevelIfEnabledGen(c)("info", Some(error), obj, ltsvable, pairs: _*)
  }

  /* Debug */
  def debugImpl(c: LoggerContext)(pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("debug", None, pairs: _*)

  def debugErrImpl(c: LoggerContext)(error: c.Expr[Throwable], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("debug", Some(error), pairs: _*)

  def debugMsgImpl(c: LoggerContext)(message: c.Expr[String]): c.Expr[Unit] =
    ltsvErrMsgLogAtLevelIfEnabled(c)("debug", None, message)

  def debugGenImpl[A: c.WeakTypeTag](c: LoggerContext)(obj: c.Expr[A], pairs: c.Expr[(String, Any)]*)(ltsvable: c.Expr[LTSVable[A]]): c.Expr[Unit] = {
    ltsvErrMsgLogAtLevelIfEnabledGen(c)("debug", None, obj, ltsvable, pairs: _*)
  }

  def debugGenErrImpl[A: c.WeakTypeTag](c: LoggerContext)(error: c.Expr[Throwable], obj: c.Expr[A], pairs: c.Expr[(String, Any)]*)(ltsvable: c.Expr[LTSVable[A]]): c.Expr[Unit] = {
    ltsvErrMsgLogAtLevelIfEnabledGen(c)("debug", Some(error), obj, ltsvable, pairs: _*)
  }

  /* Warn */
  def warnImpl(c: LoggerContext)(pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("warn", None, pairs: _*)

  def warnErrImpl(c: LoggerContext)(error: c.Expr[Throwable], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("warn", Some(error), pairs: _*)

  def warnMsgImpl(c: LoggerContext)(message: c.Expr[String]): c.Expr[Unit] =
    ltsvErrMsgLogAtLevelIfEnabled(c)("warn", None, message)

  def warnGenImpl[A: c.WeakTypeTag](c: LoggerContext)(obj: c.Expr[A], pairs: c.Expr[(String, Any)]*)(ltsvable: c.Expr[LTSVable[A]]): c.Expr[Unit] = {
    ltsvErrMsgLogAtLevelIfEnabledGen(c)("warn", None, obj, ltsvable, pairs: _*)
  }

  def warnGenErrImpl[A: c.WeakTypeTag](c: LoggerContext)(error: c.Expr[Throwable], obj: c.Expr[A], pairs: c.Expr[(String, Any)]*)(ltsvable: c.Expr[LTSVable[A]]): c.Expr[Unit] = {
    ltsvErrMsgLogAtLevelIfEnabledGen(c)("warn", Some(error), obj, ltsvable, pairs: _*)
  }

  /* Error */
  def errorImpl(c: LoggerContext)(pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("error", None, pairs: _*)

  def errorErrImpl(c: LoggerContext)(error: c.Expr[Throwable], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("error", Some(error), pairs: _*)

  def errorMsgImpl(c: LoggerContext)(message: c.Expr[String]): c.Expr[Unit] =
    ltsvErrMsgLogAtLevelIfEnabled(c)("error", None, message)

  def errorGenImpl[A: c.WeakTypeTag](c: LoggerContext)(obj: c.Expr[A], pairs: c.Expr[(String, Any)]*)(ltsvable: c.Expr[LTSVable[A]]): c.Expr[Unit] = {
    ltsvErrMsgLogAtLevelIfEnabledGen(c)("error", None, obj, ltsvable, pairs: _*)
  }

  def errorGenErrImpl[A: c.WeakTypeTag](c: LoggerContext)(error: c.Expr[Throwable], obj: c.Expr[A], pairs: c.Expr[(String, Any)]*)(ltsvable: c.Expr[LTSVable[A]]): c.Expr[Unit] = {
    ltsvErrMsgLogAtLevelIfEnabledGen(c)("error", Some(error), obj, ltsvable, pairs: _*)
  }

  /* Trace */
  def traceImpl(c: LoggerContext)(pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("trace", None, pairs: _*)

  def traceErrImpl(c: LoggerContext)(error: c.Expr[Throwable], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] =
    ltsvErrLogAtLevelIfEnabled(c)("trace", Some(error), pairs: _*)

  def traceMsgImpl(c: LoggerContext)(message: c.Expr[String]): c.Expr[Unit] =
    ltsvErrMsgLogAtLevelIfEnabled(c)("trace", None, message)

  def traceGenImpl[A: c.WeakTypeTag](c: LoggerContext)(obj: c.Expr[A], pairs: c.Expr[(String, Any)]*)(ltsvable: c.Expr[LTSVable[A]]): c.Expr[Unit] = {
    ltsvErrMsgLogAtLevelIfEnabledGen(c)("trace", None, obj, ltsvable, pairs: _*)
  }

  def traceGenErrImpl[A: c.WeakTypeTag](c: LoggerContext)(error: c.Expr[Throwable], obj: c.Expr[A], pairs: c.Expr[(String, Any)]*)(ltsvable: c.Expr[LTSVable[A]]): c.Expr[Unit] = {
    ltsvErrMsgLogAtLevelIfEnabledGen(c)("trace", Some(error), obj, ltsvable, pairs: _*)
  }

  private def ltsvErrMsgLogAtLevelIfEnabled(c: LoggerContext)(level: String, error: Option[c.Expr[Throwable]], message: c.Expr[String]): c.Expr[Unit] = {
    import c.universe._
    val pair = c.Expr[(String, String)](q"""("message" -> $message)""")
    ltsvErrLogAtLevelIfEnabled(c)(level, error, pair)
  }

  private def ltsvErrLogAtLevelIfEnabled(c: LoggerContext)(level: String, error: Option[c.Expr[Throwable]], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] = {
    import c.universe._
    val isLevelEnabled = TermName(s"is${level.toLowerCase.capitalize}Enabled")
    val logLevel = TermName(level.toLowerCase)
    val writer = c.prefix.tree
    val tree = error match {
      case Some(err) => q"if ($writer.underlyingLogger.$isLevelEnabled) $writer.underlyingLogger.$logLevel($writer.toLtsv(..$pairs), $err)"
      case None => q"if ($writer.underlyingLogger.$isLevelEnabled) $writer.underlyingLogger.$logLevel($writer.toLtsv(..$pairs))"
    }
    c.Expr[Unit](tree)
  }

  private def ltsvErrMsgLogAtLevelIfEnabledGen[A](c: LoggerContext)(level: String, error: Option[c.Expr[Throwable]], obj: c.Expr[A], ltsvable: c.Expr[LTSVable[A]], pairs: c.Expr[(String, Any)]*): c.Expr[Unit] = {
    import c.universe._
    val generatedPairs = c.Expr[Seq[(String, Any)]](q"""$ltsvable.toPairs($obj)""")
    val isLevelEnabled = TermName(s"is${level.toLowerCase.capitalize}Enabled")
    val logLevel = TermName(level.toLowerCase)
    val writer = c.prefix.tree
    val tree = error match {
      case Some(err) => q"if ($writer.underlyingLogger.$isLevelEnabled) $writer.underlyingLogger.$logLevel($writer.toLtsv(($generatedPairs ++ Seq(..$pairs)):_*), $err)"
      case None => q"if ($writer.underlyingLogger.$isLevelEnabled) $writer.underlyingLogger.$logLevel($writer.toLtsv(($generatedPairs ++ Seq(..$pairs)):_*))"
    }
    c.Expr[Unit](tree)
  }
}
