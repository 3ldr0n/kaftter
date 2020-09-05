package kaftter.consumer.configuration;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OkHttpClientConfiguration {
    private final int timeout;

    public OkHttpClientConfiguration(@Value("${kaftter-api.timeout}") final int timeout) {
        this.timeout = timeout;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(timeout))
                .build();
    }

}
