package kaftter.consumer;

import kaftter.exception.InvalidPayloadException;
import kaftter.service.TweetService;
import kaftter.tweet.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static kaftter.configuration.KafkaConsumerConfiguration.TWEET_CONSUMER_CONTAINER_FACTORY;

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
    public void consume(final Tweet message) {
        if (isNull(message)) {
            log.error("m=consume, status=null-message");
            throw new InvalidPayloadException("Empty message");
        }

        tweetService.save(message);
        log.info("m=TweetConsumerService.consume, " + message.getId());
    }

}