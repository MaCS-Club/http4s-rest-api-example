organization := "io.github.macs-club"
name := "http4s-rest-api-example"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.2"

val Http4sVersion = "0.15.11a"
val DoobieVersion = "0.4.2"

libraryDependencies ++= Seq(
 "org.http4s"     %% "http4s-blaze-server" % Http4sVersion,
 "org.http4s"     %% "http4s-circe"        % Http4sVersion,
 "org.http4s"     %% "http4s-dsl"          % Http4sVersion,
 "org.tpolecat"   %% "doobie-core"         % DoobieVersion,
 "org.tpolecat"   %% "doobie-h2"           % DoobieVersion,
 "org.tpolecat"   %% "doobie-specs2"       % DoobieVersion,
 "ch.qos.logback" %  "logback-classic"     % "1.2.1",
 // Optional for auto-derivation of JSON codecs
  "io.circe" %% "circe-generic" % "0.6.1",
  // Optional for string interpolation to JSON model
  "io.circe" %% "circe-literal" % "0.6.1"
)
