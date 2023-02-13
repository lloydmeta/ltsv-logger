package com.beachape.logging

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.slf4j.Logger

class LTSVLoggerSpec extends AnyFunSpec with MockitoSugar with Matchers {

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

  describe("Generic usage using an in-scope Ltsvavble[A]") {

    case class Request(method: String, path: String)

    implicit val reqLTSVable: LTSVable[Request] =
      (o: Request) => Seq("method" -> o.method, "path" -> o.path)

    val user = "lloyd"

    describe("#info laziness") {
      it("should not do anything, not even evaluate arguments if info is disabled") {
        var run = false
        lazy val obj = {
          run = true
          Request("GET", "/")
        }
        val (subject, underlying) = writerWithMock(infoEnabled = false)
        subject.info(obj)
        run should be(false)
      }
    }

    describe("#info without throwable") {
      it("should call .info on the underlying LoggerLike") {
        val (subject, underlying) = writerWithMock()
        subject.info(Request("GET", "/"), "user" -> user)
        verify(underlying, times(1)).info("method:GET\tpath:/\tuser:lloyd")
      }
    }

    describe("#info with throwable") {
      it("should call .info on the underlying LoggerLike") {
        val (subject, underlying) = writerWithMock()
        subject.info(exception, Request("GET", "/"), "user" -> user)
        verify(underlying, times(1)).info("method:GET\tpath:/\tuser:lloyd", exception)
      }
    }

    describe("#debug laziness") {
      it("should not do anything, not even evaluate arguments if debug is disabled") {
        var run = false
        lazy val obj = {
          run = true
          Request("GET", "/")
        }
        val (subject, underlying) = writerWithMock(debugEnabled = false)
        subject.debug(obj)
        run should be(false)
      }
    }

    describe("#debug without throwable") {
      it("should call .debug on the underlying LoggerLike") {
        val (subject, underlying) = writerWithMock()
        subject.debug(Request("GET", "/"), "user" -> user)
        verify(underlying, times(1)).debug("method:GET\tpath:/\tuser:lloyd")
      }
    }

    describe("#debug with throwable") {
      it("should call .debug on the underlying LoggerLike") {
        val (subject, underlying) = writerWithMock()
        subject.debug(exception, Request("GET", "/"), "user" -> user)
        verify(underlying, times(1)).debug("method:GET\tpath:/\tuser:lloyd", exception)
      }
    }

    describe("#warn laziness") {
      it("should not do anything, not even evaluate arguments if warn is disabled") {
        var run = false
        lazy val obj = {
          run = true
          Request("GET", "/")
        }
        val (subject, underlying) = writerWithMock(warnEnabled = false)
        subject.warn(obj)
        run should be(false)
      }
    }

    describe("#warn without throwable") {
      it("should call .warn on the underlying LoggerLike") {
        val (subject, underlying) = writerWithMock()
        subject.warn(Request("GET", "/"), "user" -> user)
        verify(underlying, times(1)).warn("method:GET\tpath:/\tuser:lloyd")
      }
    }

    describe("#warn with throwable") {
      it("should call .warn on the underlying LoggerLike") {
        val (subject, underlying) = writerWithMock()
        subject.warn(exception, Request("GET", "/"), "user" -> user)
        verify(underlying, times(1)).warn("method:GET\tpath:/\tuser:lloyd", exception)
      }
    }

    describe("#error laziness") {
      it("should not do anything, not even evaluate arguments if error is disabled") {
        var run = false
        lazy val obj = {
          run = true
          Request("GET", "/")
        }
        val (subject, underlying) = writerWithMock(errorEnabled = false)
        subject.error(obj)
        run should be(false)
      }
    }

    describe("#error without throwable") {
      it("should call .error on the underlying LoggerLike") {
        val (subject, underlying) = writerWithMock()
        subject.error(Request("GET", "/"), "user" -> user)
        verify(underlying, times(1)).error("method:GET\tpath:/\tuser:lloyd")
      }
    }

    describe("#error with throwable") {
      it("should call .error on the underlying LoggerLike") {
        val (subject, underlying) = writerWithMock()
        subject.error(exception, Request("GET", "/"), "user" -> user)
        verify(underlying, times(1)).error("method:GET\tpath:/\tuser:lloyd", exception)
      }
    }

    describe("#trace laziness") {
      it("should not do anything, not even evaluate arguments if trace is disabled") {
        var run = false
        lazy val obj = {
          run = true
          Request("GET", "/")
        }
        val (subject, underlying) = writerWithMock(traceEnabled = false)
        subject.trace(obj)
        run should be(false)
      }
    }

    describe("#trace without throwable") {
      it("should call .trace on the underlying LoggerLike") {
        val (subject, underlying) = writerWithMock()
        subject.trace(Request("GET", "/"), "user" -> user)
        verify(underlying, times(1)).trace("method:GET\tpath:/\tuser:lloyd")
      }
    }

    describe("#trace with throwable") {
      it("should call .trace on the underlying LoggerLike") {
        val (subject, underlying) = writerWithMock()
        subject.trace(exception, Request("GET", "/"), "user" -> user)
        verify(underlying, times(1)).trace("method:GET\tpath:/\tuser:lloyd", exception)
      }
    }

    describe("resolving LTSVable") {
      trait ReqHeaders {
        def method: String
        def path: String
      }

      case class ReqWithBody(method: String, path: String, body: String) extends ReqHeaders

      trait LowPriority {
        implicit val reqHeadersLtsvable: LTSVable[ReqHeaders] =
          (o: ReqHeaders) => Seq("method" -> o.method, "path" -> o.path)
      }

      object LowPriorityOnly extends LowPriority

      object HighPriority extends LowPriority {
        implicit val reqWithBodyLtsvable: LTSVable[ReqWithBody] =
          (o: ReqWithBody) => Seq("method" -> o.method, "path" -> o.path, "body" -> o.body)
      }

      describe("should work with just a parent LTSVable in scope") {
        import LowPriorityOnly.reqHeadersLtsvable
        val (subject, underlying) = writerWithMock()
        subject.trace(exception, ReqWithBody("GET", "/", "hello"), "user" -> user)
        verify(underlying, times(1)).trace("method:GET\tpath:/\tuser:lloyd", exception)
      }

      describe("should use a more specific LTSVable if one is in scope") {
        import HighPriority.reqWithBodyLtsvable
        val (subject, underlying) = writerWithMock()
        subject.trace(exception, ReqWithBody("GET", "/", "hello"), "user" -> user)
        verify(underlying, times(1)).trace("method:GET\tpath:/\tbody:hello\tuser:lloyd", exception)
      }
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
      val underlyingLogger: Logger = loggerLike
    }
    (writer, loggerLike)
  }

}
