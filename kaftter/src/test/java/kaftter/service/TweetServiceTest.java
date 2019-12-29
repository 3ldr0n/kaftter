package kaftter.service;

import kaftter.domain.TweetEntity;
import kaftter.factory.TweetFactory;
import kaftter.repository.TweetRepository;
import kaftter.vo.Tweet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetServiceTest {
    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetService tweetService;

    private TweetFactory tweetFactory;

    @Before
    public void setUp() {
        tweetFactory = new TweetFactory();
    }

    @Test
    public void getOneTweet() {
        final int numberOfTweets = 1;
        final List<TweetEntity> tweetEntityMock = tweetFactory.mockTweetsEntity(numberOfTweets);
        final Slice<TweetEntity> slice = new SliceImpl<>(tweetEntityMock,
                mock(Pageable.class), false);
        when(tweetRepository.findAll(isA(Pageable.class)))
                .thenReturn(slice);

        final List<Tweet> tweets = tweetService.findTweets(numberOfTweets);

        assertThat(tweets).isNotNull();
        assertThat(tweets).hasSize(numberOfTweets);
    }

    @Test
    public void getTenTweets() {
        final int numberOfTweets = 10;
        final List<TweetEntity> tweetEntityMock = tweetFactory.mockTweetsEntity(numberOfTweets);
        final Slice<TweetEntity> slice = new SliceImpl<>(tweetEntityMock,
                mock(Pageable.class), false);
        when(tweetRepository.findAll(isA(Pageable.class)))
                .thenReturn(slice);

        final List<Tweet> tweets = tweetService.findTweets(numberOfTweets);

        assertThat(tweets).isNotNull();
        assertThat(tweets).hasSize(numberOfTweets);
    }

}
