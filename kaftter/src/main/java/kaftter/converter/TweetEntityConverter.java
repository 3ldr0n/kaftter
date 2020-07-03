package kaftter.converter;

import kaftter.domain.TweetEntity;
import kaftter.domain.TweetKey;
import kaftter.tweet.Tweet;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@UtilityClass
public class TweetEntityConverter {
    public TweetEntity convert(final Tweet tweet) {
        final var date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(tweet.getTimestamp()),
                TimeZone.getDefault().toZoneId()
        );
        final var tweetKey = new TweetKey(tweet.getId(), date);
        return TweetEntity.builder()
                .key(tweetKey)
                .text(tweet.getText())
                .quoteCount(tweet.getQuoteCount())
                .replyCount(tweet.getReplyCount())
                .retweetCount(tweet.getRetweetCount())
                .favoriteCount(tweet.getFavoriteCount())
                .language(tweet.getLanguage())
                .timestamp(tweet.getTimestamp())
                .userId(tweet.getUser().getId())
                .userName(tweet.getUser().getName())
                .userScreenName(tweet.getUser().getScreenName())
                .userFollowers(tweet.getUser().getFollowers())
                .build();

    }
}
