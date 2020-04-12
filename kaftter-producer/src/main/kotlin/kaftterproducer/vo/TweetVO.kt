package kaftterproducer.vo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kaftter.tweet.Tweet

@JsonIgnoreProperties(ignoreUnknown = true)
data class TweetVO(
    private val id: Long,
    private val text: String,
    private val user: UserVO,
    @JsonProperty("quote_count")
    private val quoteCount: Int = 0,
    @JsonProperty("reply_count")
    private val replyCount: Int = 0,
    @JsonProperty("retweet_count")
    private val retweetCount: Int = 0,
    @JsonProperty("favorite_count")
    private val favoriteCount: Int = 0,
    @JsonProperty("lang")
    private val language: String,
    @JsonProperty("timestamp_ms")
    private val timestamp: Long
) {
    fun toAvro(): Tweet {
        val user = this.user.toAvro()
        return Tweet(
            id, text, user, quoteCount, replyCount,
            retweetCount, favoriteCount, language, timestamp
        )
    }
}