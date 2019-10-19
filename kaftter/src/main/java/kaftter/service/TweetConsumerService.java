package kaftter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kaftter.domain.TweetEntity;
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

    private final TweetRepository tweetRepository;

    public TweetConsumerService(final TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @KafkaListener(
            topics = {TWEETS_TOPIC},
            groupId = CONSUMER_GROUP_ID)
    public void consume(final String message) throws IOException {
        final var mapper = new ObjectMapper();
        final Tweet tweet = mapper.readValue(message, Tweet.class);
        log.info("m=TweetConsumerService.consume, " + tweet.getId());

        final TweetEntity tweetEntity = tweet.fromValue();
        tweetRepository.save(tweetEntity);
    }
}