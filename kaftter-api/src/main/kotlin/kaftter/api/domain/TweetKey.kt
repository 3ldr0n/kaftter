package kaftter.api.domain

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import java.time.LocalDate

@PrimaryKeyClass
data class TweetKey(
        @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
        val userId: Long,

        @PrimaryKeyColumn("created_at", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        val createdAt: LocalDate,

        @PrimaryKeyColumn(name = "tweet_id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
        val tweetId: Long
)
