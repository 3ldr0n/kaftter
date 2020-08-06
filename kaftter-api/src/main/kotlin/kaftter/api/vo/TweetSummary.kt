package kaftter.api.vo

data class TweetSummary(
        val userId: Long,
        val userName: String,
        val favoriteCount: Int,
        val quoteCount: Int,
        val replyCount: Int,
        val retweetCount: Int
)
