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
import java.util.ArrayList;
import java.util.List;
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

    public List<String> toCSV() {
        final List<String> list = new ArrayList<>(12);
        list.add(id.toString());
        list.add(text);
        list.add(String.valueOf(quoteCount));
        list.add(String.valueOf(replyCount));
        list.add(String.valueOf(retweetCount));
        list.add(String.valueOf(favoriteCount));
        list.add(language);
        list.add(timestamp.toString());
        list.add(user.getId().toString());
        list.add(user.getName());
        list.add(user.getScreenName());
        list.add(user.getFollowers().toString());
        return list;
    }
}