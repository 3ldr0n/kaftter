package kaftter.consumer.client;

import kaftter.consumer.exception.ClientException;
import kaftter.consumer.factory.TweetFactory;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KaftterApiClientTest {

    private static final String KAFTTER_URL = "http://api.kaftter.com";

    @Mock
    private OkHttpClient client;

    private KaftterApiClient kaftterApiClient;

    @BeforeEach
    void setUp() {
        kaftterApiClient = new KaftterApiClient(KAFTTER_URL, client);
    }

    @Test
    void test_successful_call_should_not_throw_exception() throws IOException {
        final var tweet = TweetFactory.mockTweet();
        mockHttpCall(200);

        kaftterApiClient.registerTweet(tweet);

        verify(client).newCall(any());
    }

    @Test
    void test_bad_request_call_should_throw_client_exception() throws IOException {
        final var tweet = TweetFactory.mockTweet();
        mockHttpCall(400);

        assertThatExceptionOfType(ClientException.class).isThrownBy(() ->
                kaftterApiClient.registerTweet(tweet)
        );

        verify(client).newCall(any());
    }

    @Test
    void test_internal_server_error_call_should_throw_ioexception() throws IOException {
        final var tweet = TweetFactory.mockTweet();
        mockHttpCall(500);

        assertThatExceptionOfType(IOException.class).isThrownBy(() ->
                kaftterApiClient.registerTweet(tweet)
        );

        verify(client).newCall(any());
    }

    @Test
    void test_error_on_network_call_should_throw_ioexception() throws IOException {
        final var tweet = TweetFactory.mockTweet();
        final var call = mock(Call.class);
        when(client.newCall(any())).thenReturn(call);
        doThrow(IOException.class).when(call).execute();

        assertThatExceptionOfType(IOException.class).isThrownBy(() ->
                kaftterApiClient.registerTweet(tweet)
        );

        verify(client).newCall(any());
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