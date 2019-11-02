package kaftter.service;

import kaftter.domain.TweetEntity;
import kaftter.repository.TweetRepository;
import kaftter.vo.Tweet;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TweetService {
    private final TweetRepository tweetRepository;

    public TweetService(final TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public List<Tweet> findTweets(final int pageSize) {
        final Pageable pages = CassandraPageRequest.of(0, pageSize);
        final Slice<TweetEntity> tweetEntities = tweetRepository.findAll(pages);

        final List<Tweet> tweets = new LinkedList<>();
        tweetEntities.forEach(tweet -> tweets.add(tweet.toValue()));
        return tweets;
    }
}
