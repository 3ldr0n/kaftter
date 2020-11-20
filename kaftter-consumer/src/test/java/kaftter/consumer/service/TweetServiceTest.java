package kaftter.consumer.service;

import kaftter.consumer.client.KaftterApiClient;
import kaftter.consumer.exception.ClientException;
import kaftter.consumer.factory.TweetFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TweetServiceTest {

    @Mock
    private KaftterApiClient kaftterApiClient;

    @InjectMocks
    private TweetService tweetService;

    @Test
    void test_successful_tweet_registration_should_not_throw_exception() throws IOException {
        final var tweet = TweetFactory.mockTweetEvent();
        doNothing().when(kaftterApiClient).registerTweet(any());

        tweetService.save(tweet);

        verify(kaftterApiClient).registerTweet(any());
    }

    @Test
    void test_client_error_on_api_call_should_rethrow_exception() throws IOException {
        final var tweet = TweetFactory.mockTweetEvent();
        doThrow(ClientException.class).when(kaftterApiClient).registerTweet(any());

        assertThatExceptionOfType(ClientException.class).isThrownBy(() ->
                tweetService.save(tweet)
        );

        verify(kaftterApiClient).registerTweet(any());
    }

    @Test
    void test_server_error_on_api_call_should_rethrow_exception() throws IOException {
        final var tweet = TweetFactory.mockTweetEvent();
        doThrow(IOException.class).when(kaftterApiClient).registerTweet(any());

        assertThatExceptionOfType(IOException.class).isThrownBy(() ->
                tweetService.save(tweet)
        );

        verify(kaftterApiClient).registerTweet(any());
    }

}