# LTSV-logger [![Build Status](https://travis-ci.org/lloydmeta/ltsv-logger.svg?branch=develop)](https://travis-ci.org/lloydmeta/ltsv-logger)

*What's LTSV?* [ltsv.org](http://ltsv.org/) has all the details, but to get you started: 

> With the LTSV format, you can parse each line by spliting with TAB (like original TSV format) easily, and extend any fields with unique labels in no particular order.

LTSV-logger is a performant and DRY LTSV logger wrapping SLF4J to provide nice LTSV logging for Scala. No need to wrap your LTSV pairs in 
`Seq`s or pay at runtime for [call-by-name](http://infoscience.epfl.ch/record/128135/files/paper.pdf) 
argument access.

It's convenient, because you can simply call log methods without checking whether the respective log level is enabled:

```scala
LTSVLogger.debug(e, "message" -> s"Some $expensive message!")
```

It's performant, because thanks to Scala macros the check-enabled-idiom is applied, just like writing this more involved code:

```scala
if (logger.isDebugEnabled) LTSVLogger.debug(e, "message" -> s"Some $expensive message!")
```

Sounds similar to [Scala Logging](https://github.com/typesafehub/scala-logging)? It's not a coincidence ;)

## Installing

Add the following to your `build.sbt`

```scala
// in your build.sbt

libraryDependencies ++= Seq(
    "com.beachape" %% "ltsv-logger" % "0.0.2", 
    "ch.qos.logback" % "logback-classic" % "1.1.2", // or any SLF4J compatible log lib you want to use
)
```

If the above does not work because it cannot be resolved, its likely because it hasn't been synced to Maven central yet.
In that case, download a SNAPSHOT release of the same version by adding this to `build.sbt`

```scala
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
    "com.beachape" %% "ltsv-logger" % "0.0.2-SNAPSHOT", 
    "ch.qos.logback" % "logback-classic" % "1.1.2", // or any slf4j compatible log lib you want to use
)
```

## Usage

```scala
import com.beachape.logging.LTSVLogger

LTSVLogger.info("hello" -> 3)
LTSVLogger.warn(new IllegalArgumentException, "hello" -> 3)

/**
  All of the above will act "lazily" at runtime and not be computed/referenced if the respective
  levels are not enabled.
  
  If varargs call-by-name was supported in Scala, the above would be implemented as 
  something like 
  
  {{{ def warn(pairs: => (String, Any) *): Unit = if (logger.isWarnEnabled) logger.warn(toLtsv(pairs)) }}}
**/
```

## Dependencies
- [SLF4J](http://www.slf4j.org/)
- [LTSV4S](https://github.com/seratch/ltsv4s)

## Caveats

* Doesn't implement the entire interface for SLF4J (yet), but should provide you with enough to do most of your logging
chores.

## Licence

The MIT License (MIT)

Copyright (c) 2014 by Lloyd Chan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.