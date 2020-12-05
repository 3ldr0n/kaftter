package kaftter.summarizer.repository

import java.time.LocalDate

import kaftter.summarizer.domain.TweetColumns
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.apache.spark.sql.functions.col

object TweetRepository {
  private val CassandraFormat = "org.apache.spark.sql.cassandra"

  /**
   * Find all tweets from yesterday.
   *
   * @param sparkSession Spark session.
   * @return A dataset with all tweets registered.
   */
  def findTweetsFromYesterday(sparkSession: SparkSession): Dataset[Row] = {
    val yesterday = LocalDate.now().minusDays(1)
    sparkSession.read
      .format(CassandraFormat)
      .option("table", "tweets")
      .option("keyspace", "kaftter")
      .load
      .select(
        col(TweetColumns.UserId.toString),
        col(TweetColumns.UserName.toString),
        col(TweetColumns.QuoteCount.toString),
        col(TweetColumns.ReplyCount.toString),
        col(TweetColumns.RetweetCount.toString),
        col(TweetColumns.FavoriteCount.toString),
      )
      .where(s"${TweetColumns.CreatedAt.toString} = ${yesterday.toString}")
  }
}
