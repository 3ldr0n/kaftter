package kaftter.service;

import kaftter.domain.TweetEntity;
import kaftter.repository.TweetRepository;
import kaftter.vo.Tweet;
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

    @Test
    public void getTweets() {
        final TweetEntity tweetEntityMock = mock(TweetEntity.class);
        final Slice<TweetEntity> slice = new SliceImpl<>(List.of(tweetEntityMock),
                mock(Pageable.class), false);
        when(tweetRepository.findAll(isA(Pageable.class)))
                .thenReturn(slice);

        final List<Tweet> tweets = tweetService.findTweets(1);

        assertThat(tweets).isNotNull();
        assertThat(tweets).hasSize(1);
    }
}
