package kaftter.consumer.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kaftter.consumer.exception.ClientException;
import kaftter.consumer.vo.Tweet;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KaftterApiClient {
    private static final String APP_NAME = "kaftter-api";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final String kaffterApiUrl;
    private final OkHttpClient client;

    public KaftterApiClient(@Value("${kaftter-api.url}") final String kaffterApiUrl,
                            final OkHttpClient client) {
        this.kaffterApiUrl = kaffterApiUrl;
        this.client = client;
    }

    @Retry(name = APP_NAME)
    @CircuitBreaker(name = APP_NAME)
    public void registerTweet(final Tweet tweet) throws IOException {
        final var payload = parseToJson(tweet);
        final var requestBody = RequestBody.create(payload, JSON);
        final var request = new Request.Builder()
                .url(kaffterApiUrl)
                .put(requestBody)
                .build();
        final var response = client.newCall(request).execute();
        handleResponse(response);
    }

    private void handleResponse(final Response response) throws IOException {
        if (HttpStatus.valueOf(response.code()).is4xxClientError()) {
            throw new ClientException(response);
        } else if (!response.isSuccessful()) {
            throw new IOException("Server error, code=" + response.code());
        }
    }

    @SneakyThrows
    private String parseToJson(final Tweet tweet) {
        final var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(tweet);
    }

}
