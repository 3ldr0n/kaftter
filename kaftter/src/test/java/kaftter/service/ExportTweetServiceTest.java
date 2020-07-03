package kaftter.service;

import kaftter.factory.TweetFactory;
import kaftter.vo.Tweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExportTweetServiceTest {
    @Mock
    private TweetService tweetService;

    @InjectMocks
    private ExportTweetService exportTweetService;

    private TweetFactory tweetFactory;

    @BeforeEach
    public void setUp() {
        tweetFactory = new TweetFactory();
    }

    @Test
    public void generateCSVWithTenTweets() throws Exception {
        final int numberOfTweets = 10;
        final List<Tweet> tweets = tweetFactory.mockTweetsVo(numberOfTweets);
        when(tweetService.findTweets(numberOfTweets)).thenReturn(tweets);

        final Resource resource = exportTweetService.exportToCSV(numberOfTweets);

        assertThat(resource).isNotNull();
        assertThat(resource.exists()).isTrue();
        assertThat(resource.getFilename()).contains("csv");
    }

    @Test
    public void generateCSVWithFiveTweetsAskingForTen() throws Exception {
        final int numberOfTweets = 10;
        final List<Tweet> tweets = tweetFactory.mockTweetsVo(5);
        when(tweetService.findTweets(numberOfTweets)).thenReturn(tweets);

        final Resource resource = exportTweetService.exportToCSV(numberOfTweets);

        assertThat(resource).isNotNull();
        assertThat(resource.exists()).isTrue();
        assertThat(resource.getFilename()).contains("csv");
    }

}
