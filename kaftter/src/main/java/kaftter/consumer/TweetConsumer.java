package kaftter.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kaftter.exception.InvalidPayloadException;
import kaftter.service.TweetService;
import kaftter.vo.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class TweetConsumer {
    private static final String EMPTY_PAYLOAD = "{}";

    private final TweetService tweetService;

    public TweetConsumer(final TweetService tweetService) {
        this.tweetService = tweetService;
    }

    /**
     * Consumes messages from the stream tweets topic and saves it in the database.
     *
     * @param message Message to be processed.
     * @throws InvalidPayloadException When an invalid payload is consumed.
     */
    @KafkaListener(
            topics = "${app.kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(final String message) throws InvalidPayloadException {
        if (isNull(message) || EMPTY_PAYLOAD.equals(message)) {
            throw new InvalidPayloadException("Empty message");
        }

        try {
            processMessage(message);
        } catch (final IOException e) {
            log.error("m=TweetConsumerService.consume, Invalid message" + message);
            throw new InvalidPayloadException("Invalid message to process");
        }
    }

    /**
     * Process a message and saves it in the database.
     *
     * @param message Message to be processed.
     * @throws IOException Error parsing the json message.
     */
    private void processMessage(final String message) throws IOException {
        final var mapper = new ObjectMapper();
        final Tweet tweet = mapper.readValue(message, Tweet.class);
        log.info("m=TweetConsumerService.consume, " + tweet.getId());
        tweetService.save(tweet);
    }
}