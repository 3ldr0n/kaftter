package kaftter.consumer.consumer;

import kaftter.consumer.service.TweetService;
import kaftter.tweet.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static kaftter.consumer.configuration.KafkaConsumerConfiguration.TWEET_CONSUMER_CONTAINER_FACTORY;

@Slf4j
@Service
public class TweetConsumer {
    private final TweetService tweetService;

    public TweetConsumer(final TweetService tweetService) {
        this.tweetService = tweetService;
    }

    /**
     * Consumes messages from the stream tweets topic and saves it in the database.
     *
     * @param message Message to be processed.
     */
    @KafkaListener(
            topics = "${kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = TWEET_CONSUMER_CONTAINER_FACTORY)
    public void consume(final ConsumerRecord<Long, Tweet> message, final Acknowledgment acknowledgment) {
        if (isNull(message) || isNull(message.value())) {
            log.warn("m=consume, status=null-message");
        } else {
            tweetService.save(message.value());
            log.info("m=TweetConsumerService.consume, " + message.value().getId());
        }

        acknowledgment.acknowledge();
    }

}