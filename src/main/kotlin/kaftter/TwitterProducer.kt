package kaftter

import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Client
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.HttpHosts
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.auth.OAuth1
import mu.KotlinLogging
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

class TwitterProducer {

    private val consumerKey = "c0XpzAWNjb1HfNVmWIrlx9P4V"
    private val consumerSecret = "yOzFZgln6BMwvFyMRbfpIugKVX1SzokmRuuwCiXXyBtv1RQoLw"
    private val token = "1147502274215862273-Krs4DgWNaivdaslglcgeO6WiYgPpXv"
    private val secret = "JLz0vMqTwCQpcSNhseFJnBFLtQS9XBcaH4DmQSKBFKJTl"

    private val logger = KotlinLogging.logger {}

    fun run() {
        val messageQueue = LinkedBlockingQueue<String>(1000)
        val client = createTwitterClient(messageQueue)
        client.connect()

        while (!client.isDone) {
            try {
                val message = messageQueue.poll(5, TimeUnit.SECONDS)
                logger.info(message)
            } catch (e: InterruptedException) {
                logger.error { e }
                client.stop()
            }
        }

        logger.info("End of Application execution.")
    }

    private fun createTwitterClient(messageQueue: BlockingQueue<String>) : Client {
        val hosts = HttpHosts(Constants.STREAM_HOST)
        val endpoint = StatusesFilterEndpoint()

        val terms = listOf("kafka")

        endpoint.trackTerms(terms)

        val auth = OAuth1(consumerKey, consumerSecret, token, secret)

        return ClientBuilder()
            .name("Hosebird-Client-01")
            .hosts(hosts)
            .authentication(auth)
            .endpoint(endpoint)
            .processor(StringDelimitedProcessor(messageQueue))
            .build()
    }
}