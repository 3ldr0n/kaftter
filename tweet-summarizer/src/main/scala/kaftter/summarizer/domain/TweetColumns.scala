package kaftter.summarizer.domain

/**
 * Columns of the tweets table.
 */
object TweetColumns extends Enumeration {
  val UserId: TweetColumns.Value = Value("user_id")
  val UserName: TweetColumns.Value = Value("user_name")
  val QuoteCount: TweetColumns.Value = Value("quote_count")
  val ReplyCount: TweetColumns.Value = Value("reply_count")
  val RetweetCount: TweetColumns.Value = Value("retweet_count")
  val FavoriteCount: TweetColumns.Value = Value("favorite_count")
  val CreatedAt: TweetColumns.Value = Value("created_at")
}
