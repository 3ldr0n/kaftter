package kaftter.vo;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TweetTest {
    @Test
    public void tweetToCsv() {
        final Tweet tweet = Tweet.builder()
                .id(1L)
                .text("test")
                .user(User.builder()
                        .id(1L)
                        .name("test user")
                        .screenName("test_user")
                        .followers(10)
                        .build())
                .quoteCount(2)
                .replyCount(3)
                .retweetCount(4)
                .favoriteCount(5)
                .language("en")
                .timestamp(12371298321L)
                .build();

        final List<String> csv = tweet.toCSV();

        assertThat(csv).isNotNull();
        assertThat(csv).hasSize(12);
    }
}
