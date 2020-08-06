package kaftter.api.resource

import kaftter.api.service.TweetService
import kaftter.api.vo.Tweet
import kaftter.api.vo.TweetSummary
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val BASE_URL = "/tweets"

@RestController
@RequestMapping(BASE_URL)
class TweetResource(
        private val tweetService: TweetService
) {
    @PutMapping
    fun put(@RequestBody tweet: Tweet): ResponseEntity<*> {
        tweetService.save(tweet)
        return ResponseEntity.ok().build<Any>()
    }

    @GetMapping("/{userId}")
    fun get(@PathVariable("userId") userId: Long): ResponseEntity<TweetSummary> {
        val tweetSummary = tweetService.search(userId)
        return ResponseEntity.ok().body(tweetSummary)
    }
}
