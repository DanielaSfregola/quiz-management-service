enablePlugins(JavaServerAppPackaging)

name := "quiz-management-service"

version := "0.1"

organization := "com.danielasfregola"

scalaVersion := "2.11.5"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
                  Resolver.bintrayRepo("hseeberger", "maven"))

libraryDependencies ++= {
  val AkkaVersion       = "2.3.9"
  val AkkaHttpVersion   = "2.0.1"
  val PlayJsonVersion   = "2.5.8"
  Seq(
    "com.typesafe.akka" %% "akka-slf4j"      % AkkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % AkkaHttpVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.1.2",
    "de.heikoseeberger" %% "akka-http-play-json" % "1.10.1",
    "com.typesafe.play" %% "play-json" % PlayJsonVersion
  )
}

// Assembly settings
mainClass in Global := Some("com.danielasfregola.quiz.management.Main")

jarName in assembly := "quiz-management-server.jar"
