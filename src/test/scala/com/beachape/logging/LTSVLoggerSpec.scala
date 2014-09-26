package com.beachape.logging

import org.scalatest.{ Matchers, FunSpec }
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.slf4j.{ Marker, Logger }

class LTSVLoggerSpec extends FunSpec with MockitoSugar with Matchers {

  val exception = new IllegalArgumentException

  describe("#debug laziness") {
    it("should not do anything, not even evaluate arguments if debug is disabled") {
      var run = false
      lazy val pair = {
        run = true
        ("hi" -> "there")
      }
      val (subject, underlying) = writerWithMock(debugEnabled = false)
      subject.debug(pair)
      run should be(false)
    }
  }

  describe("#debug and not passing a Throwable") {
    it("should call .debug on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.debug("hi" -> "there")
      verify(underlying, times(1)).debug("hi:there")
    }
  }

  describe("#debug and passing a single string") {
    it("should call .debug on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.debug("hi")
      verify(underlying, times(1)).debug("message:hi")
    }
  }

  describe("#debug and passing a Throwable") {
    it("should call .debug on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.debug(exception, "hello" -> "there", "lloyd" -> "meta")
      verify(underlying, times(1)).debug("hello:there\tlloyd:meta", exception)
    }
  }

  describe("#info laziness") {
    it("should not do anything, not even evaluate arguments if info is disabled") {
      var run = false
      lazy val pair = {
        run = true
        ("hi" -> "there")
      }
      val (subject, underlying) = writerWithMock(infoEnabled = false)
      subject.info(pair)
      run should be(false)
    }
  }

  describe("#info and not passing a Throwable") {
    it("should call .info on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.info("hi" -> "there")
      verify(underlying, times(1)).info("hi:there")
    }
  }

  describe("#info and passing a single string") {
    it("should call .info on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.info("hi")
      verify(underlying, times(1)).info(anyString())
    }
  }

  describe("#info and passing a Throwable") {
    it("should call .info on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.info(exception, "hello" -> "there", "info" -> "throwable")
      verify(underlying, times(1)).info("hello:there\tinfo:throwable", exception)
    }
  }

  describe("#warn laziness") {
    it("should not do anything, not even evaluate arguments if warn is disabled") {
      var run = false
      lazy val pair = {
        run = true
        ("hi" -> "there")
      }
      val (subject, underlying) = writerWithMock(warnEnabled = false)
      subject.warn(pair)
      run should be(false)
    }
  }

  describe("#warn and not passing a Throwable") {
    it("should call .warn on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.warn("hi" -> "there")
      verify(underlying, times(1)).warn("hi:there")
    }
  }

  describe("#warn and passing a single string") {
    it("should call .warn on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.warn("hi")
      verify(underlying, times(1)).warn("message:hi")
    }
  }

  describe("#warn and passing a Throwable") {
    it("should call .warn on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.warn(exception, "hello" -> "there")
      verify(underlying, times(1)).warn("hello:there", exception)
    }
  }

  describe("#error laziness") {
    it("should not do anything, not even evaluate arguments if error is disabled") {
      var run = false
      lazy val pair = {
        run = true
        ("hi" -> "there")
      }
      val (subject, underlying) = writerWithMock(errorEnabled = false)
      subject.error(pair)
      run should be(false)
    }
  }

  describe("#error and not passing a Throwable") {
    it("should call .error on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.error("hi" -> "there")
      verify(underlying, times(1)).error("hi:there")
    }
  }

  describe("#error and passing a single string") {
    it("should call .error on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.error("hi")
      verify(underlying, times(1)).error(anyString())
    }
  }

  describe("#error and passing a Throwable") {
    it("should call .error on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.error(exception, "hello" -> "there")
      verify(underlying, times(1)).error("hello:there", exception)
    }
  }

  describe("#trace laziness") {
    it("should not do anything, not even evaluate arguments if trace is disabled") {
      var run = false
      lazy val pair = {
        run = true
        ("hi" -> "there")
      }
      val (subject, underlying) = writerWithMock(traceEnabled = false)
      subject.trace(pair)
      run should be(false)
    }
  }

  describe("#trace and not passing a Throwable") {
    it("should call .trace on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.trace("hi" -> "there")
      verify(underlying, times(1)).trace("hi:there")
    }
  }

  describe("#trace and passing a single string") {
    it("should call .trace on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.trace("hi")
      verify(underlying, times(1)).trace("message:hi")
    }
  }

  describe("#trace and passing a Throwable") {
    it("should call .trace on the underlying LoggerLike") {
      val (subject, underlying) = writerWithMock()
      subject.trace(exception, "hello" -> "there", "trace" -> "throwable")
      verify(underlying, times(1)).trace("hello:there\ttrace:throwable", exception)
    }
  }

  def writerWithMock(debugEnabled: Boolean = true,
                     errorEnabled: Boolean = true,
                     infoEnabled: Boolean = true,
                     traceEnabled: Boolean = true,
                     warnEnabled: Boolean = true): (LTSVLoggerLike, Logger) = {
    val loggerLike = mock[Logger]
    when(loggerLike.isDebugEnabled).thenReturn(debugEnabled)
    when(loggerLike.isErrorEnabled).thenReturn(errorEnabled)
    when(loggerLike.isInfoEnabled).thenReturn(infoEnabled)
    when(loggerLike.isTraceEnabled).thenReturn(traceEnabled)
    when(loggerLike.isWarnEnabled).thenReturn(warnEnabled)
    val writer = new LTSVLoggerLike {
      val underlying: Logger = loggerLike
    }
    (writer, loggerLike)
  }

}
