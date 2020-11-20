package kaftter.consumer.converter;

import kaftter.consumer.factory.TweetFactory;
import kaftter.tweet.Tweet;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class TweetConverterTest {

    @Test
    void test_conversion_with_null_tweet_should_throw_exception() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() ->
                TweetConverter.convert(null)
        );
    }

    @Test
    void test_conversion_with_null_user_should_throw_exception() {
        final var tweet = new Tweet(0L, "", null, 0, 0, 0, 0, "en");
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() ->
                TweetConverter.convert(tweet)
        );
    }

    @Test
    void test_conversion_with_all_fields_valid_should_convert_successfully() {
        final var tweetEvent = TweetFactory.mockTweetEvent();
        final var tweet = TweetConverter.convert(tweetEvent);

        assertThat(tweet.getId()).isEqualTo(tweet.getId());
        assertThat(tweet.getText()).isEqualTo(tweet.getText());
        assertThat(tweet.getQuoteCount()).isEqualTo(tweet.getQuoteCount());
        assertThat(tweet.getReplyCount()).isEqualTo(tweet.getReplyCount());
        assertThat(tweet.getRetweetCount()).isEqualTo(tweet.getRetweetCount());
        assertThat(tweet.getFavoriteCount()).isEqualTo(tweet.getFavoriteCount());
        assertThat(tweet.getLanguage()).isEqualTo(tweet.getLanguage());
        assertThat(tweet.getUser().getId()).isEqualTo(tweetEvent.getUser().getId());
        assertThat(tweet.getUser().getName()).isEqualTo(tweetEvent.getUser().getName());
        assertThat(tweet.getUser().getScreenName()).isEqualTo(tweetEvent.getUser().getScreenName());
        assertThat(tweet.getUser().getFollowers()).isEqualTo(tweetEvent.getUser().getFollowers());
    }

}