
name := """scala-samples"""

version := "1.0.0"

lazy val root = (project in file("."))

scalaVersion := "2.11.5"

resolvers in Global ++= Seq(
  "Maven central" at "http://repo1.maven.org/maven2/",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe repository 2" at "http://repo.typesafe.com/typesafe/maven-releases/",
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "SpringSource Milestone Repository" at "http://repo.springsource.org/milestone",
  "Pentaho releases" at "http://repository.pentaho.org/artifactory/repo/",
  "Excilys repository" at "http://repository.excilys.com/content/groups/public",
  "Unear repository" at "http://archiva.unear.net/repository/internal/"
)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-actors" % "2.11.5",
  "com.typesafe.akka" %% "akka-actor" % "2.3.9",
  "com.typesafe.akka" %% "akka-transactor" % "2.3.9",
  "org.apache.httpcomponents" % "httpclient" % "4.2.5",
  "com.google.guava" % "guava" % "18.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "junit" % "junit" % "4.10",
  "org.seleniumhq.selenium" % "selenium-java" % "2.44.0",
  "org.specs2" %% "specs2" % "2.0",
  "org.codehaus.jackson" % "jackson-jaxrs" % "1.9.13",
  "org.json4s" %% "json4s-native" % "3.2.11",
  "org.json4s" %% "json4s-jackson" % "3.2.11",
  "net.unear" %% "domain" % "1.0.2",
  "joda-time" % "joda-time" % "2.7",
  "com.basho.riak" % "riak-client" % "1.1.0",
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "org.webjars" % "jquery" % "2.1.3",
  "org.webjars" % "bootstrap" % "3.3.1",
  "org.webjars" % "angularjs" % "1.3.8",
  "org.webjars" % "angular-ui-router" % "0.2.13"
)

dependencyOverrides += "org.webjars" % "jquery" % "2.1.3"
