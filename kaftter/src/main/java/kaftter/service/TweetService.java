package kaftter.service;

import kaftter.converter.TweetEntityConverter;
import kaftter.repository.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TweetService {
    private final TweetRepository tweetRepository;

    public TweetService(final TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public void save(final kaftter.tweet.Tweet tweet) {
        final var tweetEntity = TweetEntityConverter.convert(tweet);
        tweetRepository.save(tweetEntity);
    }

}
