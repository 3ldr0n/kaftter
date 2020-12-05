name := "tweet-summarizer"

version := "1.0"

scalaVersion := "2.12.10"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.1"
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "3.0.0"
