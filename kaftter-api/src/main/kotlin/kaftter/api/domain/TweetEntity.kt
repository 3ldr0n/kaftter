package kaftter.api.domain

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

const val TWEETS = "tweets"

/**
 * Tweets table.
 */
@Table(TWEETS)
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

        @Column("language")
        val language: String,

        @Column("user_name")
        val userName: String,

        @Column("user_screen_name")
        val userScreenName: String,

        @Column("user_followers_count")
        val userFollowers: Int
)
