package kaftter.api.domain

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn

@PrimaryKeyClass
data class TweetKey(
        @PrimaryKeyColumn(name = "tweet_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        val tweetId: Long,

        @PrimaryKeyColumn(name = "user_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
        val userId: Long
)