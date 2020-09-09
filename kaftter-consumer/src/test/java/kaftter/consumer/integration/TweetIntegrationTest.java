package kaftter.consumer.integration;

import kaftter.consumer.consumer.TweetConsumer;
import kaftter.consumer.exception.ClientException;
import kaftter.consumer.factory.TweetFactory;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.support.Acknowledgment;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TweetIntegrationTest {
    private static final String KAFTTER_URL = "http://api.kaftter.com";

    @MockBean
    private OkHttpClient client;

    @Autowired
    private TweetConsumer tweetConsumer;

    @Test
    public void test_success() throws IOException {
        final var tweetEvent = TweetFactory.mockTweetEvent();
        final var consumerRecord = new ConsumerRecord<>("stream.tweets", 0, 1, tweetEvent.getUser().getId(), tweetEvent);
        final var acknowledgment = mock(Acknowledgment.class);
        mockHttpCall(200);

        tweetConsumer.consume(consumerRecord, acknowledgment);

        verify(acknowledgment).acknowledge();
        verify(client).newCall(any());
    }

    @Test
    public void test_client_error_on_api_call_should_throw_exception_and_not_acknowledge() throws IOException {
        final var tweetEvent = TweetFactory.mockTweetEvent();
        final var consumerRecord = new ConsumerRecord<>("stream.tweets", 0, 1, tweetEvent.getUser().getId(), tweetEvent);
        final var acknowledgment = mock(Acknowledgment.class);
        mockHttpCall(400);

        assertThatExceptionOfType(ClientException.class).isThrownBy(() ->
                tweetConsumer.consume(consumerRecord, acknowledgment)
        );

        verify(acknowledgment, never()).acknowledge();
        verify(client).newCall(any());
    }

    @Test
    public void test_internal_server_error_on_api_call_should_throw_exception_and_not_acknowledge() throws IOException {
        final var tweetEvent = TweetFactory.mockTweetEvent();
        final var consumerRecord = new ConsumerRecord<>("stream.tweets", 0, 1, tweetEvent.getUser().getId(), tweetEvent);
        final var acknowledgment = mock(Acknowledgment.class);
        mockHttpCall(500);

        assertThatExceptionOfType(IOException.class).isThrownBy(() ->
                tweetConsumer.consume(consumerRecord, acknowledgment)
        );

        verify(acknowledgment, never()).acknowledge();
        verify(client, times(2)).newCall(any());
    }

    @Test
    public void test_network_error_on_api_call_should_throw_exception_and_not_acknowledge() throws IOException {
        final var tweetEvent = TweetFactory.mockTweetEvent();
        final var consumerRecord = new ConsumerRecord<>("stream.tweets", 0, 1, tweetEvent.getUser().getId(), tweetEvent);
        final var acknowledgment = mock(Acknowledgment.class);
        final var call = mock(Call.class);
        when(client.newCall(any())).thenReturn(call);
        doThrow(IOException.class).when(call).execute();

        assertThatExceptionOfType(IOException.class).isThrownBy(() ->
                tweetConsumer.consume(consumerRecord, acknowledgment)
        );

        verify(acknowledgment, never()).acknowledge();
        verify(client, times(2)).newCall(any());
    }

    /**
     * Mock HTTP call response.
     *
     * @param statusCode Response status code.
     */
    private void mockHttpCall(final int statusCode) throws IOException {
        final var call = mock(Call.class);
        when(client.newCall(any())).thenReturn(call);
        final var response = new Response.Builder()
                .request(new Request.Builder().url(KAFTTER_URL).build())
                .protocol(Protocol.HTTP_2)
                .message("message")
                .code(statusCode)
                .build();
        when(call.execute()).thenReturn(response);
    }

}
