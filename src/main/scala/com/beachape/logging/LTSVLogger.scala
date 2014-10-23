package com.beachape.logging

import com.github.seratch.ltsv4s.LTSV
import org.slf4j.{ LoggerFactory, Logger => Slf4jLogger }

/**
 * SLF4JLogger wrapper for writing log messages to a dedicated logger in LTSV format in a
 * performant way.
 *
 * The logging methods in this trait are performant because the check-enabled-idiom
 * is applied using macros. For example,
 *
 * {{{
 * logger.info("message" -> s"$expensiveMessage"*)
 * }}}
 *
 * gets expanded at compile-time to
 *
 * {{{
 * if (logger.isDebugEnabled) logger.info(toLtsv("message" -> s"$expensiveMessage"*))
 * }}}
 */
trait LTSVLoggerLike {

  import scala.language.experimental.macros

  /**
   * The logger to write to.
   */
  def underlying: Slf4jLogger

  /* Info */
  /**
   * Write the given key-values as an LTSV info log entry
   */
  @inline final def info(pairs: (String, Any)*): Unit = macro LTSVLogWriterMacros.infoImpl

  /**
   * Write the given message as an LTSV info log entry. Convenient, but don't over-use it,
   * otherwise, the entire point of LTSV is lost.
   *
   * The LTSV log entry has a singular "message" key
   */
  @inline final def info(message: String): Unit = macro LTSVLogWriterMacros.infoMsgImpl

  /**
   * Write the given key-values and error as an LTSV info log entry
   */
  @inline final def info(error: Throwable, pairs: (String, Any)*): Unit = macro LTSVLogWriterMacros.infoErrImpl

  /**
   * Write a given Generic [A] object into LTSV along with any number of pairs
   */
  @inline final def info[A](obj: A, pairs: (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit = macro LTSVLogWriterMacros.infoGenImpl[A]

  /**
   * Write a given Generic [A] object and error into LTSV along with any number of pairs
   */
  @inline final def info[A](error: Throwable, obj: A, pairs: (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit = macro LTSVLogWriterMacros.infoGenErrImpl[A]

  /* Debug */
  /**
   * Write the given key-values as an LTSV debug log entry
   */
  @inline final def debug(pairs: (String, Any)*): Unit = macro LTSVLogWriterMacros.debugImpl

  /**
   * Write the given message as an LTSV info debug entry. Convenient, but don't over-use it,
   * otherwise, the entire point of LTSV is lost.
   *
   * The LTSV log entry has a singular "message" key
   */
  @inline final def debug(message: String): Unit = macro LTSVLogWriterMacros.debugMsgImpl

  /**
   * Write the given key-values and error as an LTSV debug log entry
   */
  @inline final def debug(error: Throwable, pairs: (String, Any)*): Unit = macro LTSVLogWriterMacros.debugErrImpl

  /**
   * Write a given Generic [A] object into LTSV along with any number of pairs
   */
  @inline final def debug[A](obj: A, pairs: (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit = macro LTSVLogWriterMacros.debugGenImpl[A]

  /**
   * Write a given Generic [A] object and error into LTSV along with any number of pairs
   */
  @inline final def debug[A](error: Throwable, obj: A, pairs: (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit = macro LTSVLogWriterMacros.debugGenErrImpl[A]

  /* Warn */
  /**
   * Write the given key-values as an LTSV warn log entry
   */
  @inline final def warn(pairs: (String, Any)*): Unit = macro LTSVLogWriterMacros.warnImpl

  /**
   * Write the given message as an LTSV info warn entry. Convenient, but don't over-use it,
   * otherwise, the entire point of LTSV is lost.
   *
   * The LTSV log entry has a singular "message" key
   */
  @inline final def warn(message: String): Unit = macro LTSVLogWriterMacros.warnMsgImpl

  /**
   * Write the given key-values and error as an LTSV warn log entry
   */
  @inline final def warn(error: Throwable, pairs: (String, Any)*): Unit = macro LTSVLogWriterMacros.warnErrImpl

  /**
   * Write a given Generic [A] object into LTSV along with any number of pairs
   */
  @inline final def warn[A](obj: A, pairs: (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit = macro LTSVLogWriterMacros.warnGenImpl[A]

  /**
   * Write a given Generic [A] object and error into LTSV along with any number of pairs
   */
  @inline final def warn[A](error: Throwable, obj: A, pairs: (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit = macro LTSVLogWriterMacros.warnGenErrImpl[A]

  /* Error */
  /**
   * Write the given key-values as an LTSV error log entry
   */
  @inline final def error(pairs: (String, Any)*): Unit = macro LTSVLogWriterMacros.errorImpl

  /**
   * Write the given message as an LTSV info error entry. Convenient, but don't over-use it,
   * otherwise, the entire point of LTSV is lost.
   *
   * The LTSV log entry has a singular "message" key
   */
  @inline final def error(message: String): Unit = macro LTSVLogWriterMacros.errorMsgImpl

  /**
   * Write the given key-values and error as an LTSV error log entry
   */
  @inline final def error(error: Throwable, pairs: (String, Any)*): Unit = macro LTSVLogWriterMacros.errorErrImpl

  /**
   * Write a given Generic [A] object into LTSV along with any number of pairs
   */
  @inline final def error[A](obj: A, pairs: (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit = macro LTSVLogWriterMacros.errorGenImpl[A]

  /**
   * Write a given Generic [A] object and error into LTSV along with any number of pairs
   */
  @inline final def error[A](error: Throwable, obj: A, pairs: (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit = macro LTSVLogWriterMacros.errorGenErrImpl[A]

  /* Trace */
  /**
   * Write the given key-values as an LTSV trace log entry
   */
  @inline final def trace(pairs: (String, Any)*): Unit = macro LTSVLogWriterMacros.traceImpl

  /**
   * Write the given message as an LTSV info trace entry. Convenient, but don't over-use it,
   * otherwise, the entire point of LTSV is lost.
   *
   * The LTSV log entry has a singular "message" key
   */
  @inline final def trace(message: String): Unit = macro LTSVLogWriterMacros.traceMsgImpl

  /**
   * Write the given key-values and error as an LTSV trace log entry
   */
  @inline final def trace(error: Throwable, pairs: (String, Any)*): Unit = macro LTSVLogWriterMacros.traceErrImpl

  /**
   * Write a given Generic [A] object into LTSV along with any number of pairs
   */
  @inline final def trace[A](obj: A, pairs: (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit = macro LTSVLogWriterMacros.traceGenImpl[A]

  /**
   * Write a given Generic [A] object and error into LTSV along with any number of pairs
   */
  @inline final def trace[A](error: Throwable, obj: A, pairs: (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit = macro LTSVLogWriterMacros.traceGenErrImpl[A]

  /**
   * Turns a sequence of LTSV pairs into a single LTSV string.
   */
  @inline def toLtsv(pairs: (String, Any)*): String = {
    val logMsg = pairs.map { case (k, v) => (k, String.valueOf(v)) }
    LTSV.dump(logMsg: _*)
  }

}

class LTSVLogger(val underlying: Slf4jLogger) extends LTSVLoggerLike

object LTSVLogger extends LTSVLoggerLike {

  val underlying = LoggerFactory.getLogger("application")

  /**
   * Obtains a logger instance.
   *
   * @param name the name of the logger
   * @return a logger
   */
  def apply(name: String): LTSVLogger = new LTSVLogger(LoggerFactory.getLogger(name))

  /**
   * Obtains a logger instance.
   *
   * @param clazz a class whose name will be used as logger name
   * @return a logger
   */
  def apply[T](clazz: Class[T]): LTSVLogger = new LTSVLogger(LoggerFactory.getLogger(clazz))
}