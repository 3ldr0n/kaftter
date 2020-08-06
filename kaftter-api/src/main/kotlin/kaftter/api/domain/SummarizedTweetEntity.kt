package kaftter.api.domain

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

const val SUMMARIZED_USER_TWEETS = "summarized_user_tweets"

/**
 * Stores summarized information from a user.
 */
@Table(SUMMARIZED_USER_TWEETS)
data class SummarizedTweetEntity(
        @PrimaryKey("user_id")
        val userId: Long,

        @Column("user_name")
        val userName: String,

        @Column("quote_count")
        val quoteCount: Int,

        @Column("reply_count")
        val replyCount: Int,

        @Column("retweet_count")
        val retweetCount: Int,

        @Column("favorite_count")
        val favoriteCount: Int
)