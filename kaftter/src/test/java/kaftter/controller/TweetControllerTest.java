package kaftter.controller;

import kaftter.service.TweetService;
import kaftter.vo.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetControllerTest {

    @Mock
    private TweetService tweetService;

    @InjectMocks
    private TweetController tweetController;

    @Test
    public void getTenTweets() throws Exception {
        final int pageSize = 10;
        final List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            tweets.add(mock(Tweet.class));
        }
        when(tweetService.findTweets(pageSize))
                .thenReturn(tweets);

        final ResponseEntity response = tweetController.getTweets(pageSize);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
