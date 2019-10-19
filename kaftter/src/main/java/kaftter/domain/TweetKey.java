package kaftter.domain;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@PrimaryKeyClass
public class TweetKey implements Serializable {
    @PrimaryKeyColumn(name = "tweet_id")
    private Long tweetId;

    @PrimaryKeyColumn(name = "created_at", type = PrimaryKeyType.PARTITIONED)
    private LocalDateTime createdAt;

    public TweetKey(final Long tweetId, final LocalDateTime createdAt) {
        this.tweetId = tweetId;
        this.createdAt = createdAt;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(final Long tweetId) {
        this.tweetId = tweetId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TweetKey tweetKey = (TweetKey) o;
        return tweetId.equals(tweetKey.tweetId) &&
                createdAt.equals(tweetKey.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tweetId, createdAt);
    }
}
