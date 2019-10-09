package kaftter.domain

import kaftter.vo.Tweet
import kaftter.vo.User
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("tweets")
data class TweetEntity(
        @PrimaryKey
        val key: TweetKey,
        val text: String,
        @Column("quote_count")
        val quoteCount: Int,
        @Column("reply_count")
        val replyCount: Int,
        @Column("retweet_count")
        val retweetCount: Int,
        @Column("favorite_count")
        val favoriteCount: Int,
        val language: String,
        val timestamp: String,
        @Column("user_id")
        val userId: Long,
        @Column("user_name")
        val userName: String,
        @Column("user_screen_name")
        val userScreenName: String
) {

    fun toValue(): Tweet {
        val user = User(userId, userName, userScreenName)
        return Tweet(
                createdAt = key.createdAt,
                id = key.tweetId,
                text = text,
                user = user,
                quoteCount = quoteCount,
                replyCount = replyCount,
                retweetCount = retweetCount,
                favoriteCount = favoriteCount,
                language = language,
                timestamp = timestamp
        )
    }
}