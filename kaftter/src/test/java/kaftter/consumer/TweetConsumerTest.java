package kaftter.consumer;

import kaftter.domain.TweetEntity;
import kaftter.exception.InvalidPayloadException;
import kaftter.repository.TweetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetConsumerTest {
    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetConsumer tweetConsumer;

    @Test
    public void consumeAndSave() throws Exception {
        final String payload = readValidPayload();
        final TweetEntity tweetMock = mock(TweetEntity.class);
        when(tweetRepository.save(any(TweetEntity.class)))
                .thenReturn(tweetMock);

        tweetConsumer.consume(payload);

        verify(tweetRepository, times(1)).save(any(TweetEntity.class));
    }

    @Test(expected = InvalidPayloadException.class)
    public void consumeEmptyPayload() throws Exception {
        final var payload = "{}";

        tweetConsumer.consume(payload);

        verify(tweetRepository, never()).save(any(TweetEntity.class));
    }

    @Test(expected = InvalidPayloadException.class)
    public void consumeInvalidPayload() throws Exception {
        final var payload = "definitely not json";

        tweetConsumer.consume(payload);

        verify(tweetRepository, never()).save(any(TweetEntity.class));
    }

    @Test(expected = InvalidPayloadException.class)
    public void consumerNullMessage() throws Exception {
        tweetConsumer.consume(null);

        verify(tweetRepository, never()).save(any(TweetEntity.class));
    }

    private String readValidPayload() throws IOException {
        final File validPayloadFile = new ClassPathResource("valid_payload.json").getFile();
        return new String(Files.readAllBytes(validPayloadFile.toPath()));
    }
}