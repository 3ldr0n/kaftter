package kaftter.service;

import kaftter.domain.TweetEntity;
import kaftter.repository.TweetRepository;
import kaftter.vo.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetServiceTest {
    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetService tweetService;

    @Test
    public void get50Tweets() throws Exception {
        final Slice sliceOfTweetEntities = mock(Slice.class);
        when(tweetRepository.findAll(PageRequest.of(0, 50)))
                .thenReturn(sliceOfTweetEntities);

        final List<Tweet> tweets = tweetService.findTweets();

        assertThat(tweets).isNotNull();
        assertThat(tweets).hasSize(50);
    }
}
