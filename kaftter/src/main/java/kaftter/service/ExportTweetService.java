package kaftter.service;

import kaftter.domain.TweetCSVHeaders;
import kaftter.vo.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Service responsible for exporting tweets in the database to a file format.
 */
@Slf4j
@Service
public class ExportTweetService {
    private final TweetService tweetService;

    public ExportTweetService(final TweetService tweetService) {
        this.tweetService = tweetService;
    }

    /**
     * Generates a CSV file containing a certain number of tweets.
     *
     * @param numberOfTweets number of tweets to be fetched.
     * @return URL resource for the file.
     * @throws IOException when an error occurs writing the file.
     */
    public Resource exportToCSV(final int numberOfTweets) throws IOException {
        final List<Tweet> tweets = tweetService.findTweets(numberOfTweets);
        final File temp = generateCSV(tweets);

        final UrlResource urlResource = buildUrlResource(temp);

        if (urlResource.exists()) {
            return urlResource;
        }
        throw new FileNotFoundException("File " + urlResource.getFilename() + " not found");
    }

    /**
     * Build URL Resource from the file.
     *
     * @param file File to be exported.
     * @return URL Resource for the file.
     * @throws FileNotFoundException When there's an error with the url.
     */
    private UrlResource buildUrlResource(final File file) throws IOException {
        try {
            return new UrlResource(file.toURI());
        } catch (final MalformedURLException e) {
            throw new IOException("Error building file for download.");
        }
    }

    /**
     * Generates the CSV file for the given tweets.
     *
     * @param tweets Tweets to be saved on the file.
     * @return The file instance
     * @throws IOException When an error writing the file occurs.
     */
    private File generateCSV(final List<Tweet> tweets) throws IOException {
        final File temp = File.createTempFile("temp.csv.", ".tmp");
        final FileWriter writer = new FileWriter(temp);
        try (final CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(TweetCSVHeaders.class))) {
            tweets.forEach(tweet ->
                    registerCSVLine(printer, tweet)
            );
            writer.flush();
            writer.close();
        }
        return temp;
    }

    /**
     * Registers a tweet in a line of a CSV file.
     *
     * @param printer CSV printer.
     * @param tweet   Tweet to be registered.
     */
    private void registerCSVLine(final CSVPrinter printer, final Tweet tweet) {
        try {
            printer.printRecord(tweet.toCSV());
            printer.flush();
        } catch (final IOException e) {
            log.error("error", e);
        }
    }

}
