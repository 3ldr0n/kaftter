package kaftter.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import kaftter.domain.TweetEntity;
import kaftter.repository.TweetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetConsumerServiceTest {
    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetConsumerService tweetConsumerService;

    @Test
    public void consume_andSave() throws Exception {
        final String payload = readValidPayload();
        final TweetEntity tweetMock = mock(TweetEntity.class);
        when(tweetRepository.save(any(TweetEntity.class)))
                .thenReturn(tweetMock);

        tweetConsumerService.consume(payload);

        verify(tweetRepository, times(1)).save(any(TweetEntity.class));
    }

    private String readValidPayload() throws IOException {
        final File validPayloadFile = new ClassPathResource("valid_payload.json").getFile();
        return new String(Files.readAllBytes(validPayloadFile.toPath()));
    }
}