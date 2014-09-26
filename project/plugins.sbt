// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += Classpaths.sbtPluginReleases

// scoverage for test coverage
addSbtPlugin("org.scoverage" %% "sbt-scoverage" % "0.99.7.1")

// for code formatting
addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0")

// For publishing coverage data to coveralls.io
addSbtPlugin("org.scoverage" %% "sbt-coveralls" % "0.99.0")
