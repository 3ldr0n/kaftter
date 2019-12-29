package kaftter.controller;

import kaftter.factory.TweetFactory;
import kaftter.service.TweetService;
import kaftter.vo.Tweet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetControllerTest {

    @Mock
    private TweetService tweetService;

    @InjectMocks
    private TweetController tweetController;

    private TweetFactory tweetFactory;

    @Before
    public void setUp() {
        tweetFactory = new TweetFactory();
    }

    @Test
    public void getTenTweets() {
        final int pageSize = 10;
        final List<Tweet> tweets = tweetFactory.mockTweetsVo(pageSize);
        when(tweetService.findTweets(pageSize))
                .thenReturn(tweets);

        final ResponseEntity<List<Tweet>> response = tweetController.getTweets(pageSize);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
