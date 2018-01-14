import steam.build.Nexus

name := "play-statsd-play26"
    
organization := "com.gmi_mr.play.plugins"

version := Nexus.latest("253.0")

scalaVersion := "2.11.12"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.6.11" % "provided",
  "com.typesafe.play"  %% "play-test" % "2.6.11" % "test",
  "org.specs2" %% "specs2-core" % "4.0.2",
  "org.specs2" %% "specs2-junit" % "4.0.2",
  "org.specs2" %% "specs2-mock" % "4.0.2",
  specs2 % "test"
)

parallelExecution in Test := false

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

resolvers ++= Nexus.repoUrls

publishTo := Nexus.publishUrl

credentials += Credentials("Sonatype Nexus Repository Manager", Nexus.host, Nexus.username, Nexus.password)
 
javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation", "-encoding", "UTF-8")

scalacOptions += "-deprecation"
  
lazy val root = project in file(".")

lazy val sample = (project in file("sample/sample-statsd"))
  .enablePlugins(PlayScala)
  .settings(
    Keys.fork in Test := false,
    scalaVersion := "2.11.12",
    scalacOptions += "-deprecation",
    libraryDependencies ++= Seq(
      ws,
      "com.typesafe.play" %% "play" % "2.6.11" % "provided",
      "com.typesafe.play"  %% "play-test" % "2.6.11" % "test",
      "org.specs2" %% "specs2-core" % "4.0.2",
      "org.specs2" %% "specs2-junit" % "4.0.2",
      "org.specs2" %% "specs2-mock" % "4.0.2",
      specs2 % "test"
    )
  ).dependsOn(root).aggregate(root)
