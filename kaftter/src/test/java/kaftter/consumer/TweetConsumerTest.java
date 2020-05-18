package kaftter.consumer;

import kaftter.exception.InvalidPayloadException;
import kaftter.service.TweetService;
import kaftter.tweet.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TweetConsumerTest {
    @Mock
    private TweetService tweetService;

    @InjectMocks
    private TweetConsumer tweetConsumer;

    @Test
    public void consumer_valid_message_should_save_to_database() {
        final var payload = new Tweet();
        doNothing().when(tweetService).save(payload);

        tweetConsumer.consume(payload);

        verify(tweetService).save(any(Tweet.class));
    }

    @Test
    public void consume_null_message_should_throw_exception() {
        assertThatExceptionOfType(InvalidPayloadException.class).isThrownBy(() ->
                tweetConsumer.consume(null)
        );

        verify(tweetService, never()).save(any(Tweet.class));
    }

}