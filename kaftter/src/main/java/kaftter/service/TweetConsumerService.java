package kaftter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kaftter.domain.TweetEntity;
import kaftter.exception.InvalidPayloadException;
import kaftter.repository.TweetRepository;
import kaftter.vo.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class TweetConsumerService {
    private static final String TWEETS_TOPIC = "stream.tweets";
    private static final String CONSUMER_GROUP_ID = "tweets-consumer";
    private static final String EMPTY_PAYLOAD = "{}";

    private final TweetRepository tweetRepository;

    public TweetConsumerService(final TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @KafkaListener(
            topics = {TWEETS_TOPIC},
            groupId = CONSUMER_GROUP_ID)
    public void consume(final String message) throws InvalidPayloadException {
        if (EMPTY_PAYLOAD.equals(message)) {
            throw new InvalidPayloadException("Empty message");
        }

        final var mapper = new ObjectMapper();
        try {
            final Tweet tweet = mapper.readValue(message, Tweet.class);
            log.info("m=TweetConsumerService.consume, " + tweet.getId());
            final TweetEntity tweetEntity = tweet.fromValue();
            tweetRepository.save(tweetEntity);
        } catch (final IOException e) {
            log.error("m=TweetConsumerService.consume, Invalid message" + message);
            throw new InvalidPayloadException("Invalid message to process");
        }
    }
}