package kaftter.summarizer

import org.apache.spark.sql.SparkSession

object TweetSummarizer {
  val appName = "tweet-summarizer"

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName(appName)
      .getOrCreate()

    val data = spark.read
      .csv("/tmp/tweet-summarizer/input_data.csv")

    data.write
      .json("/tmp/tweet-summarizer/output")
  }
}
