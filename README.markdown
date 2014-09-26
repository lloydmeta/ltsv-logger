# LTSV-logger [![Build Status](https://travis-ci.org/lloydmeta/ltsv-logger.svg?branch=develop)](https://travis-ci.org/lloydmeta/ltsv-logger)

Performant and DRY LTSV logger wrapping SLF4J to provide nice LTSV logging for Scala.

Doesn't implement the entire interface for SLF4J (yet), but should provide you with enough to do most of your logging
chores.

It's convenient, because you can simply call log methods without checking whether the respective log level is enabled:

```scala
LTSVLogger.debug(e, "message" -> s"Some $expensive message!")
```

It's performant, because thanks to Scala macros the check-enabled-idiom is applied, just like writing this more involved code:

```scala
if (logger.isDebugEnabled) LTSVLogger.debug(e, "message" -> s"Some $expensive message!")
```

Dependencies include:
- [SLF4J](http://www.slf4j.org/)
- [LTSV4S](https://github.com/seratch/ltsv4s)

## Installing

Add the following to your `build.sbt`

```scala
// in your build.sbt

libraryDependencies ++= Seq(
    "com.beachape" %% "ltsv-logger" % "0.0.1", 
    "ch.qos.logback" % "logback-classic" % "1.1.2", // or any SLF4J compatible log lib you want to use
)
```

If the above does not work because it cannot be resolved, its likely because it hasn't been synced to Maven central yet.
In that case, download a SNAPSHOT release of the same version by adding this to `build.sbt`

```scala
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
    "com.beachape" %% "ltsv-logger" % "0.0.1-SNAPSHOT", 
    "ch.qos.logback" % "logback-classic" % "1.1.2", // or any slf4j compatible log lib you want to use
)
```
