package kaftter.service

import com.google.gson.Gson
import kaftter.vo.Tweet
import org.apache.commons.logging.LogFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

private const val tweetsTopic = "stream.tweets"

@Service
class TweetConsumerService {

    private val logger = LogFactory.getLog(TweetConsumerService::class.java)

    @KafkaListener(topics = [tweetsTopic])
    fun consume(message: String) {
        val gson = Gson()
        val tweet = gson.fromJson(message, Tweet::class.java)
        logger.info("m=TweetConsumerService.consume, $tweet")
    }
}