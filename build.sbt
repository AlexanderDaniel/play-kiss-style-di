name := """play-kiss-style-di"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

com.jamesward.play.BrowserNotifierKeys.shouldOpenBrowser := false

libraryDependencies += "org.scalatestplus" %% "play" % "1.1.0" % "test"