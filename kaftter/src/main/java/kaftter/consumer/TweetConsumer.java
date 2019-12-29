package kaftter.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kaftter.domain.TweetEntity;
import kaftter.exception.InvalidPayloadException;
import kaftter.repository.TweetRepository;
import kaftter.vo.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class TweetConsumer {
    private static final String TWEETS_TOPIC = "stream.tweets";
    private static final String CONSUMER_GROUP_ID = "tweets-consumer";
    private static final String EMPTY_PAYLOAD = "{}";

    private final TweetRepository tweetRepository;

    public TweetConsumer(final TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    /**
     * Consumes messages from the stream tweets topic and saves it in the database.
     *
     * @param message Message to be processed.
     * @throws InvalidPayloadException When an invalid payload is consumed.
     */
    @KafkaListener(
            topics = {TWEETS_TOPIC},
            groupId = CONSUMER_GROUP_ID)
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
        final TweetEntity tweetEntity = tweet.fromValue();
        tweetRepository.save(tweetEntity);
    }
}