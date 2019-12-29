package kaftter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@PrimaryKeyClass
@AllArgsConstructor
public class TweetKey implements Serializable {
    @PrimaryKeyColumn(name = "tweet_id")
    private Long tweetId;

    @PrimaryKeyColumn(name = "created_at", type = PrimaryKeyType.PARTITIONED)
    private LocalDateTime createdAt;
}
