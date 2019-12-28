package kaftter.service;

import kaftter.domain.TweetCSVHeaders;
import kaftter.domain.TweetEntity;
import kaftter.repository.TweetRepository;
import kaftter.vo.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TweetService {
    private final TweetRepository tweetRepository;

    public TweetService(final TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public List<Tweet> findTweets(final int pageSize) {
        final Pageable pages = CassandraPageRequest.of(0, pageSize);
        final Slice<TweetEntity> tweetEntities = tweetRepository.findAll(pages);

        final List<Tweet> tweets = new LinkedList<>();
        tweetEntities.forEach(tweet -> tweets.add(tweet.toValue()));
        return tweets;
    }

    public Resource generateCsvFile(final int numberOfTweets) throws IOException {
        final List<Tweet> tweets = findTweets(numberOfTweets);
        final File temp = File.createTempFile("temp.csv", ".tmp");
        final FileWriter writer = new FileWriter(temp);
        try (final CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(TweetCSVHeaders.class))) {
            tweets.forEach(tweet -> {
                try {
                    printer.printRecord(tweet.toCSV());
                    printer.flush();
                } catch (final IOException e) {
                    log.error("error", e);
                }
            });
            writer.flush();
        }

        return new ClassPathResource(temp.getPath());
    }
}
