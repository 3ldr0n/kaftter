package kaftter.api.vo

data class Tweet(
        val id: Long,
        val text: String,
        val user: User,
        val quoteCount: Int,
        val replyCount: Int,
        val retweetCount: Int,
        val favoriteCount: Int,
        val language: String
)
