package kaftter.api.service

import kaftter.api.converter.convert
import kaftter.api.exception.UserNotFoundException
import kaftter.api.repository.SummarizedTweetRepository
import kaftter.api.repository.TweetRepository
import kaftter.api.vo.Tweet
import kaftter.api.vo.TweetSummary
import org.springframework.stereotype.Service

@Service
class TweetService(
        private val tweetRepository: TweetRepository,
        private val summarizedTweetRepository: SummarizedTweetRepository
) {
    fun save(tweet: Tweet) {
        tweetRepository.save(convert(tweet))
    }

    fun search(userId: Long): TweetSummary {
        val summarizedTweet = summarizedTweetRepository.findByUserId(userId)
        summarizedTweet?.let {
            return convert(it)
        }
        throw UserNotFoundException(userId)
    }

}
