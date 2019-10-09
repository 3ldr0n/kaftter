package kaftter.domain

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn

@PrimaryKeyClass
data class TweetKey (
        @PrimaryKeyColumn(name = "id")
        val id: Long,
        @PrimaryKeyColumn(name = "tweet_id")
        val tweetId: Long,
        @PrimaryKeyColumn(name = "created_at", type = PrimaryKeyType.PARTITIONED)
        val createdAt: String
)