import com.typesafe.sbt.SbtScalariform._
import sbt._
import sbt.Keys._
import scoverage.ScoverageSbtPlugin
import scoverage.ScoverageSbtPlugin._
import org.scoverage.coveralls.CoverallsPlugin.coverallsSettings

import scala.language.postfixOps
import scalariform.formatter.preferences._

object LTSVLoggerBuild extends Build {

  val theVersion = "0.0.5"
  val theScalaVersion = "2.11.2"

  val slf4jVersion = "1.7.7"

  lazy val commonSettings = Seq(
    organization := "com.beachape",
    version := theVersion,
    scalaVersion := theScalaVersion
  ) ++
    scalariformSettings ++
    formatterPrefs ++
    compilerSettings ++
    resolverSettings ++
    ideSettings ++
    testSettings ++
    coverallsSettings ++
    scoverageSettings

  lazy val commonWithPublishSettings =
    commonSettings ++
    publishSettings

  lazy val resolverSettings = Seq(
    resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
    resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  )

  lazy val ideSettings = Seq(
    // Faster "sbt gen-idea"
    transitiveClassifiers in Global := Seq(Artifact.SourceClassifier)
  )

  lazy val compilerSettings = Seq(
    // the name-hashing algorithm for the incremental compiler.
    incOptions := incOptions.value.withNameHashing(nameHashing = true),
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xlint")
  )

  lazy val testSettings = Seq(Test, ScoverageSbtPlugin.ScoverageTest).flatMap { t =>
    Seq(parallelExecution in t := false) // Avoid DB-related tests stomping on each other
  }

  lazy val scoverageSettings =
    instrumentSettings ++
    Seq(
      ScoverageKeys.highlighting := true
    )

  lazy val formatterPrefs = Seq(
    ScalariformKeys.preferences := ScalariformKeys.preferences.value
      .setPreference(AlignParameters, true)
      .setPreference(DoubleIndentClassDeclaration, true)
  )

  lazy val root = Project(id = "ltsv-logger", base = file("."), settings = commonWithPublishSettings)
    .settings(
      name := "ltsv-logger",
      crossScalaVersions := Seq("2.10.4", "2.11.2"),
      crossVersion := CrossVersion.binary,
      libraryDependencies ++= Seq(
        "org.scala-lang" % "scala-reflect" % scalaVersion.value,
        "org.slf4j" % "slf4j-api" % slf4jVersion,
        "com.github.seratch" %% "ltsv4s" % "1.0.2",
        "org.mockito" % "mockito-all" % "1.9.5" % "test",
        "org.scalatest" %% "scalatest" % "2.2.1" % "test"
      ) ++ {
        val additionalMacroDeps = CrossVersion.partialVersion(scalaVersion.value) match {
          // if scala 2.11+ is used, quasiquotes are merged into scala-reflect
          case Some((2, scalaMajor)) if scalaMajor >= 11 =>
            Nil
          // in Scala 2.10, quasiquotes are provided by macro paradise
          case Some((2, 10)) =>
            Seq(
              compilerPlugin("org.scalamacros" % "paradise" % "2.0.0" cross CrossVersion.full),
              "org.scalamacros" %% "quasiquotes" % "2.0.0" cross CrossVersion.binary)
        }
        additionalMacroDeps }
    )

  // Settings for publishing to Maven Central
  lazy val publishSettings = Seq(
    pomExtra :=
      <url>https://github.com/lloydmeta/ltsv-logger</url>
      <licenses>
        <license>
          <name>MIT</name>
          <url>http://opensource.org/licenses/MIT</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:lloydmeta/ltsv-logger.git</url>
        <connection>scm:git:git@github.com:lloydmeta/ltsv-logger.git</connection>
      </scm>
      <developers>
        <developer>
          <id>lloydmeta</id>
          <name>Lloyd Chan</name>
          <url>http://lloydmeta.github.io</url>
        </developer>
      </developers>,
    publishTo <<= version { v =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false }
  )

}
