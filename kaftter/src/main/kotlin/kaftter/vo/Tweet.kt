package kaftter.vo

import com.google.gson.annotations.SerializedName
import kaftter.domain.TweetEntity
import kaftter.domain.TweetKey
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone

data class Tweet(
    val id: Long,
    val text: String,
    val user: User,
    val quoteCount: Int,
    val replyCount: Int,
    val retweetCount: Int,
    val favoriteCount: Int,
    @SerializedName("lang")
    val language: String,
    @SerializedName("timestamp_ms")
    val timestamp: Long
) {

    fun fromValue(): TweetEntity {
        val date = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            TimeZone.getDefault().toZoneId()
        )
        val tweetKey = TweetKey(id, date)
        return TweetEntity(
            key = tweetKey,
            text = text,
            quoteCount = quoteCount,
            replyCount = replyCount,
            retweetCount = retweetCount,
            favoriteCount = favoriteCount,
            language = language,
            timestamp = timestamp,
            userId = user.id,
            userName = user.name,
            userScreenName = user.screenName
        )
    }
}