import sbt._

ThisBuild / organization := "org.test"
ThisBuild / version := "1.0.0"
ThisBuild / fork              := true
ThisBuild / scalaVersion      := "3.2.0"

val ZioVersion = "2.0.2"

val `zio-test`     = "dev.zio" %% "zio-test"     % ZioVersion % Test
val `zio-test-sbt` = "dev.zio" %% "zio-test-sbt" % ZioVersion % Test

lazy val root = (project in file("."))
  .settings(
    name := "testStacktrace",
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    libraryDependencies ++= Seq(`zio-test`, `zio-test-sbt`),
  )
