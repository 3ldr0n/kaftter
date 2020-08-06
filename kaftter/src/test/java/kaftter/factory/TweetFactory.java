package kaftter.factory;

import kaftter.domain.TweetEntity;
import kaftter.vo.Tweet;
import kaftter.vo.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

@Component
public class TweetFactory {

    final AtomicLong longIds = new AtomicLong();
    final AtomicInteger intIds = new AtomicInteger();

    public List<TweetEntity> mockTweetsEntity(final int numberOfTweets) {
        final List<Tweet> tweets = mockTweetsVo(numberOfTweets);
        final List<TweetEntity> entities = new LinkedList<>();
        tweets.forEach(tweet -> entities.add(tweet.fromValue()));
        return entities;
    }

    public List<Tweet> mockTweetsVo(final int numberOfTweets) {
        final List<Tweet> tweets = new LinkedList<>();
        final LocalDateTime now = LocalDateTime.now();
        final Long timestamp = now.toEpochSecond(ZoneOffset.UTC);

        LongStream.range(0, numberOfTweets)
                .forEach(i ->
                        tweets.add(
                                Tweet.builder()
                                        .id(longIds.getAndIncrement())
                                        .text("test")
                                        .user(User.builder()
                                                .id(longIds.getAndIncrement())
                                                .name("test user")
                                                .screenName("test_user")
                                                .followers(intIds.getAndIncrement())
                                                .build())
                                        .quoteCount(intIds.getAndIncrement())
                                        .replyCount(intIds.getAndIncrement())
                                        .retweetCount(intIds.getAndIncrement())
                                        .favoriteCount(intIds.getAndIncrement())
                                        .language("en")
                                        .timestamp(timestamp)
                                        .build()
                        )
                );

        return tweets;
    }

}
