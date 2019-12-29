package kaftter.controller;

import kaftter.service.ExportTweetService;
import kaftter.service.TweetService;
import kaftter.vo.Tweet;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
    private final TweetService tweetService;
    private final ExportTweetService exportTweetService;

    public TweetController(final TweetService tweetService, final ExportTweetService exportTweetService) {
        this.tweetService = tweetService;
        this.exportTweetService = exportTweetService;
    }

    @GetMapping("/{numberOfTweets}")
    public ResponseEntity<List<Tweet>> getTweets(@PathVariable("numberOfTweets") final int numberOfTweets) {
        final List<Tweet> tweets = tweetService.findTweets(numberOfTweets);
        return ResponseEntity.status(HttpStatus.OK).body(tweets);
    }

    @GetMapping("/export/{numberOfTweets}")
    public ResponseEntity<Resource> exportTweets(@PathVariable("numberOfTweets") final int numberOfTweets) {
        try {
            final Resource resource = exportTweetService.exportToCSV(numberOfTweets);

            final MediaType type = new MediaType("text", "csv");

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(type)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename())
                    .body(resource);
        } catch (final IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
