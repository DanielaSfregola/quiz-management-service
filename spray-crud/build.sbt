enablePlugins(JavaServerAppPackaging)

name := "quiz-management-service"

version := "0.1"

organization := "com.danielasfregola"

scalaVersion := "2.11.5"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
                  "Spray Repository"    at "http://repo.spray.io")

libraryDependencies ++= {
  val AkkaVersion       = "2.3.9"
  val SprayVersion      = "1.3.2"
  val Json4sVersion     = "3.2.11"
  Seq(
    "io.spray"          %% "spray-can"       % SprayVersion,
    "io.spray"          %% "spray-routing"   % SprayVersion,
    "com.typesafe.akka" %% "akka-slf4j"      % AkkaVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.1.2",
    "org.json4s"        %% "json4s-native"   % Json4sVersion,
    "org.json4s"        %% "json4s-ext"      % Json4sVersion
  )
}

// Assembly settings
mainClass in Global := Some("com.danielasfregola.quiz.management.Main")

jarName in assembly := "quiz-management-server.jar"
