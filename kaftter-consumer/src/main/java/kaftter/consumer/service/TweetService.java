package kaftter.consumer.service;

import kaftter.consumer.client.KaftterApiClient;
import kaftter.consumer.converter.TweetConverter;
import kaftter.tweet.Tweet;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TweetService {
    private final KaftterApiClient kaftterApiClient;

    public TweetService(final KaftterApiClient kaftterApiClient) {
        this.kaftterApiClient = kaftterApiClient;
    }

    @SneakyThrows
    public void save(final Tweet tweet) {
        kaftterApiClient.registerTweet(TweetConverter.convert(tweet));
    }

}
