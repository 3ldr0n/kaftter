package kaftter.controller;

import kaftter.service.TweetService;
import kaftter.vo.Tweet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
    private final TweetService tweetService;

    public TweetController(final TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    public ResponseEntity getTweets() {
        final List<Tweet> tweets = tweetService.findTweets();
        return ResponseEntity.ok().body(tweets);
    }
}
