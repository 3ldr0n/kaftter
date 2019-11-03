package kaftter.controller;

import kaftter.service.TweetService;
import kaftter.vo.Tweet;
import org.springframework.core.io.Resource;
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

    public TweetController(final TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/{pageSize}")
    public ResponseEntity getTweets(@PathVariable("pageSize") final int pageSize) {
        final List<Tweet> tweets = tweetService.findTweets(pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(tweets);
    }

    @GetMapping("/export/{pageSize}")
    public ResponseEntity exportTweets(@PathVariable("pageSize") final int pageSize) {
        try {
            final Resource resource = tweetService.generateCsvFile(pageSize);

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(resource);
        } catch (final IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
