package kaftter.summarizer

import kaftter.summarizer.repository.TweetRepository
import org.apache.spark.sql.SparkSession

object TweetSummarizer {
  private val AppName = "tweet-summarizer"

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName(AppName)
      .config("spark.cassandra.connection.host", "127.0.0.1")
      .config("spark.cassandra.connection.port", "9042")
      .getOrCreate()

    val data = TweetRepository.findTweetsFromYesterday(spark)

    data.write
      .json("/tmp/tweet-summarizer/output")
  }
}
