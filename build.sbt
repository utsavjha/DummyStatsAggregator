name := "dummyStatsAggregator"
version := "1.0"
scalaVersion := "2.12.8"

// Universal Plugin Settings

val akkaVersion = "2.5.21"
val akkaHttpVersion = "10.1.7"
val circeVersion = "0.11.1"

libraryDependencies ++= Seq(
  "ch.qos.logback"                    %  "logback-classic"              % "1.2.3",
  "org.slf4j"                         %  "jul-to-slf4j"                 % "1.7.26",
  "com.typesafe.scala-logging"        %% "scala-logging"                % "3.9.2",
  "com.iheart"                        %% "ficus"                        % "1.4.5",

  "com.typesafe.akka"                 %% "akka-actor"                   % akkaVersion,
  "com.typesafe.akka"                 %% "akka-http"                    % akkaHttpVersion,
  "com.typesafe.akka"                 %% "akka-stream"                  % akkaVersion,
  "com.typesafe.akka"                 %% "akka-stream-contrib"          % "0.9",
  "com.typesafe.akka"                 %% "akka-stream-kafka"            % "1.0.1",

  "io.circe"                          %% "circe-core"                   % circeVersion,
  "io.circe"                          %% "circe-generic"                % circeVersion,
  "io.circe"                          %% "circe-parser"                 % circeVersion
)
