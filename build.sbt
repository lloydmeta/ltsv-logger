val theVersion = "0.1.1-SNAPSHOT"
val theScalaVersion = "3.2.2"

val slf4jVersion = "1.7.36"

lazy val commonSettings = Seq(
  organization := "com.beachape",
  version := theVersion,
  scalaVersion := theScalaVersion
) ++
  compilerSettings ++
  testSettings

lazy val commonWithPublishSettings =
  commonSettings ++
    publishSettings

lazy val compilerSettings = Seq(
  scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, _)) =>
        Seq("-unchecked", "-deprecation", "-feature", "-Xlint")
      case _ =>
        Seq("-unchecked", "-deprecation", "-feature")
    }
  }
)

lazy val testSettings = Seq(
  // Avoid DB-related tests stomping on each other
  Test / parallelExecution := false
)

lazy val root = Project(id = "ltsv-logger", base = file(".")).settings(commonWithPublishSettings)
  .settings(
    name := "ltsv-logger",
    crossScalaVersions := Seq("3.2.2", "2.13.10", "2.12.17"),
    crossVersion := CrossVersion.binary,
    libraryDependencies ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, _)) =>
          Seq("org.scala-lang" % "scala-reflect" % scalaVersion.value)
        case _ =>
          Nil
      }
    } ++ Seq(
      "org.slf4j" % "slf4j-api" % slf4jVersion,
      "com.github.seratch" %% "ltsv4s" % "1.1.0",
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "org.scalatestplus" %% "mockito-4-6" % "3.2.15.0" % Test
    )
  )

// Settings for publishing to Maven Central
lazy val publishSettings = Seq(
  pomExtra :=
    <url>https://github.com/lloydmeta/ltsv-logger</url>
      <licenses>
        <license>
          <name>MIT</name>
          <url>https://opensource.org/licenses/MIT</url>
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
          <url>https://beachape.com/</url>
        </developer>
      </developers>,
  publishTo := version { v =>
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  }.value,
  publishMavenStyle := true,
  Test / publishArtifact := false,
  pomIncludeRepository := { _ => false }
)
