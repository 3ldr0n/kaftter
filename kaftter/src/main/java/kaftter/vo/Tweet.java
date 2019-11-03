package kaftter.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import kaftter.domain.TweetEntity;
import kaftter.domain.TweetKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.StringJoiner;
import java.util.TimeZone;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {
    private Long id;
    private String text;
    private User user;
    @JsonProperty("quote_count")
    private int quoteCount;
    @JsonProperty("reply_count")
    private int replyCount;
    @JsonProperty("retweet_count")
    private int retweetCount;
    @JsonProperty("favorite_count")
    private int favoriteCount;
    @JsonProperty("lang")
    private String language;
    @JsonProperty("timestamp_ms")
    private Long timestamp;
    private static final String CSV_SEPARATOR = ",";

    public TweetEntity fromValue() {
        final var date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                TimeZone.getDefault().toZoneId()
        );
        final var tweetKey = new TweetKey(id, date);
        return TweetEntity.builder()
                .key(tweetKey)
                .text(text)
                .quoteCount(quoteCount)
                .replyCount(replyCount)
                .retweetCount(retweetCount)
                .favoriteCount(favoriteCount)
                .language(language)
                .timestamp(timestamp)
                .userId(user.getId())
                .userName(user.getName())
                .userScreenName(user.getScreenName())
                .userFollowers(user.getFollowers())
                .build();
    }

    public String toCSV() {
        final StringJoiner joiner = new StringJoiner(CSV_SEPARATOR);
        joiner.add(id.toString());
        joiner.add(text);
        joiner.add(String.valueOf(quoteCount));
        joiner.add(String.valueOf(replyCount));
        joiner.add(String.valueOf(retweetCount));
        joiner.add(String.valueOf(favoriteCount));
        joiner.add(language);
        joiner.add(timestamp.toString());
        joiner.add(user.getId().toString());
        joiner.add(user.getName());
        joiner.add(user.getScreenName());
        joiner.add(user.getFollowers().toString());
        return joiner.toString();
    }
}