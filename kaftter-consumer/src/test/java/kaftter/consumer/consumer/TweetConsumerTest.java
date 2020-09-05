package kaftter.consumer.consumer;

import kaftter.consumer.service.TweetService;
import kaftter.tweet.Tweet;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.support.Acknowledgment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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
        final var consumerRecord = new ConsumerRecord<>("", 0, 0, 0L, payload);
        final var acknowledgment = mock(Acknowledgment.class);
        doNothing().when(tweetService).save(payload);

        tweetConsumer.consume(consumerRecord, acknowledgment);

        verify(tweetService).save(any(Tweet.class));
        verify(acknowledgment).acknowledge();
    }

    @Test
    public void consume_null_consumer_record_should_acknowledge_message_and_not_save() {
        final var acknowledgment = mock(Acknowledgment.class);

        tweetConsumer.consume(null, acknowledgment);

        verify(tweetService, never()).save(any(Tweet.class));
        verify(acknowledgment).acknowledge();
    }

    @Test
    public void consume_null_message_on_consumer_record_should_acknowledge_message_and_not_save() {
        final var acknowledgment = mock(Acknowledgment.class);
        final var consumerRecord = new ConsumerRecord<Long, Tweet>("", 0, 0, 0L, null);

        tweetConsumer.consume(consumerRecord, acknowledgment);

        verify(tweetService, never()).save(any(Tweet.class));
        verify(acknowledgment).acknowledge();
    }

}