package kaftter.configuration;

import kaftter.tweet.Tweet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class KafkaConsumerConfiguration {
    public static final String TWEET_CONSUMER_CONTAINER_FACTORY = "tweetConsumerContainerFactory";

    private final Integer concurrency;

    public KafkaConsumerConfiguration(@Value("kafka.concurrency") final Integer concurrency) {
        this.concurrency = concurrency;
    }

    @Bean(TWEET_CONSUMER_CONTAINER_FACTORY)
    public ConcurrentKafkaListenerContainerFactory<Long, Tweet> tweetConsumerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<Long, Tweet> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(concurrency);
        return factory;
    }

}
