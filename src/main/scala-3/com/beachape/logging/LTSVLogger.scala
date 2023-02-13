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
 * if (logger.isInfoEnabled) logger.info(toLtsv("message" -> s"$expensiveMessage"*))
 * }}}
 */
trait LTSVLoggerLike:

  /**
   * The logger to write to.
   */
  def underlyingLogger: Slf4jLogger

  /* Info */
  /**
   * Write the given key-values as an LTSV info log entry
   */
  inline def info(pairs: => (String, Any)*): Unit =
    if (underlyingLogger.isInfoEnabled)
      underlyingLogger.info(toLtsv(pairs: _*))

  /**
   * Write the given message as an LTSV info log entry. Convenient, but don't over-use it,
   * otherwise, the entire point of LTSV is lost.
   *
   * The LTSV log entry has a singular "message" key
   */
  inline def info(message: String): Unit =
    if (underlyingLogger.isInfoEnabled)
      underlyingLogger.info(toLtsv("message" -> message))

  /**
   * Write the given key-values and error as an LTSV info log entry
   */
  inline def info(error: Throwable, pairs: => (String, Any)*): Unit =
    if (underlyingLogger.isInfoEnabled)
      underlyingLogger.info(toLtsv(pairs: _*), error)

  /**
   * Write a given Generic [A] object into LTSV along with any number of pairs
   */
  inline def info[A](obj: A, pairs: => (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit =
    if (underlyingLogger.isInfoEnabled)
      underlyingLogger.info(toLtsv((ltsvable.toPairs(obj) ++ pairs): _*))

  /**
   * Write a given Generic [A] object and error into LTSV along with any number of pairs
   */
  inline def info[A](error: Throwable, obj: A, pairs: => (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit =
    if (underlyingLogger.isInfoEnabled)
      underlyingLogger.info(toLtsv((ltsvable.toPairs(obj) ++ pairs): _*), error)

  /* Debug */
  /**
   * Write the given key-values as an LTSV debug log entry
   */
  inline def debug(pairs: => (String, Any)*): Unit =
    if (underlyingLogger.isDebugEnabled)
      underlyingLogger.debug(toLtsv(pairs: _*))

  /**
   * Write the given message as an LTSV info debug entry. Convenient, but don't over-use it,
   * otherwise, the entire point of LTSV is lost.
   *
   * The LTSV log entry has a singular "message" key
   */
  inline def debug(message: String): Unit =
    if (underlyingLogger.isDebugEnabled)
      underlyingLogger.debug(toLtsv("message" -> message))

  /**
   * Write the given key-values and error as an LTSV debug log entry
   */
  inline def debug(error: Throwable, pairs: => (String, Any)*): Unit =
    if (underlyingLogger.isDebugEnabled)
      underlyingLogger.debug(toLtsv(pairs: _*), error)

  /**
   * Write a given Generic [A] object into LTSV along with any number of pairs
   */
  inline def debug[A](obj: A, pairs: => (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit =
    if (underlyingLogger.isDebugEnabled)
      underlyingLogger.debug(toLtsv((ltsvable.toPairs(obj) ++ pairs): _*))

  /**
   * Write a given Generic [A] object and error into LTSV along with any number of pairs
   */
  inline def debug[A](error: Throwable, obj: A, pairs: => (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit =
    if (underlyingLogger.isDebugEnabled)
      underlyingLogger.debug(toLtsv((ltsvable.toPairs(obj) ++ pairs): _*), error)

  /* Warn */
  /**
   * Write the given key-values as an LTSV warn log entry
   */
  inline def warn(pairs: => (String, Any)*): Unit =
    if (underlyingLogger.isWarnEnabled)
      underlyingLogger.warn(toLtsv(pairs: _*))

  /**
   * Write the given message as an LTSV info warn entry. Convenient, but don't over-use it,
   * otherwise, the entire point of LTSV is lost.
   *
   * The LTSV log entry has a singular "message" key
   */
  inline def warn(message: String): Unit =
    if (underlyingLogger.isWarnEnabled)
      underlyingLogger.warn(toLtsv("message" -> message))

  /**
   * Write the given key-values and error as an LTSV warn log entry
   */
  inline def warn(error: Throwable, pairs: => (String, Any)*): Unit =
    if (underlyingLogger.isWarnEnabled)
      underlyingLogger.warn(toLtsv(pairs: _*), error)

  /**
   * Write a given Generic [A] object into LTSV along with any number of pairs
   */
  inline def warn[A](obj: A, pairs: => (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit =
    if (underlyingLogger.isWarnEnabled)
      underlyingLogger.warn(toLtsv((ltsvable.toPairs(obj) ++ pairs): _*))

  /**
   * Write a given Generic [A] object and error into LTSV along with any number of pairs
   */
  inline def warn[A](error: Throwable, obj: A, pairs: => (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit =
    if (underlyingLogger.isWarnEnabled)
      underlyingLogger.warn(toLtsv((ltsvable.toPairs(obj) ++ pairs): _*), error)

  /* Error */
  /**
   * Write the given key-values as an LTSV error log entry
   */
  inline def error(pairs: => (String, Any)*): Unit =
    if (underlyingLogger.isErrorEnabled)
      underlyingLogger.error(toLtsv(pairs: _*))

  /**
   * Write the given message as an LTSV info error entry. Convenient, but don't over-use it,
   * otherwise, the entire point of LTSV is lost.
   *
   * The LTSV log entry has a singular "message" key
   */
  inline def error(message: String): Unit =
    if (underlyingLogger.isErrorEnabled)
      underlyingLogger.error(toLtsv("message" -> message))

  /**
   * Write the given key-values and error as an LTSV error log entry
   */
  inline def error(error: Throwable, pairs: => (String, Any)*): Unit =
    if (underlyingLogger.isErrorEnabled)
      underlyingLogger.error(toLtsv(pairs: _*), error)

  /**
   * Write a given Generic [A] object into LTSV along with any number of pairs
   */
  inline def error[A](obj: A, pairs: => (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit =
    if (underlyingLogger.isErrorEnabled)
      underlyingLogger.error(toLtsv((ltsvable.toPairs(obj) ++ pairs): _*))

  /**
   * Write a given Generic [A] object and error into LTSV along with any number of pairs
   */
  inline def error[A](error: Throwable, obj: A, pairs: => (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit =
    if (underlyingLogger.isErrorEnabled)
      underlyingLogger.error(toLtsv((ltsvable.toPairs(obj) ++ pairs): _*), error)

  /* Trace */
  /**
   * Write the given key-values as an LTSV trace log entry
   */
  inline def trace(pairs: => (String, Any)*): Unit =
    if (underlyingLogger.isTraceEnabled)
      underlyingLogger.trace(toLtsv(pairs: _*))

  /**
   * Write the given message as an LTSV info trace entry. Convenient, but don't over-use it,
   * otherwise, the entire point of LTSV is lost.
   *
   * The LTSV log entry has a singular "message" key
   */
  inline def trace(message: String): Unit =
    if (underlyingLogger.isTraceEnabled)
      underlyingLogger.trace(toLtsv("message" -> message))

  /**
   * Write the given key-values and error as an LTSV trace log entry
   */
  inline def trace(error: Throwable, pairs: => (String, Any)*): Unit =
    if (underlyingLogger.isTraceEnabled)
      underlyingLogger.trace(toLtsv(pairs: _*), error)

  /**
   * Write a given Generic [A] object into LTSV along with any number of pairs
   */
  inline def trace[A](obj: A, pairs: => (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit =
    if (underlyingLogger.isTraceEnabled)
      underlyingLogger.trace(toLtsv((ltsvable.toPairs(obj) ++ pairs): _*))

  /**
   * Write a given Generic [A] object and error into LTSV along with any number of pairs
   */
  inline def trace[A](error: Throwable, obj: A, pairs: => (String, Any)*)(implicit ltsvable: LTSVable[A]): Unit =
    if (underlyingLogger.isTraceEnabled)
      underlyingLogger.trace(toLtsv((ltsvable.toPairs(obj) ++ pairs): _*), error)

  /**
   * Turns a sequence of LTSV pairs into a single LTSV string.
   */
  def toLtsv(pairs: => (String, Any)*): String =
    val logMsg = pairs.map { case (k, v) => (k, String.valueOf(v)) }
    LTSV.dump(logMsg: _*)

class LTSVLogger(val underlyingLogger: Slf4jLogger) extends LTSVLoggerLike

object LTSVLogger extends LTSVLoggerLike:

  val underlyingLogger = LoggerFactory.getLogger("application")

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
