package kaftter

import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Client
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.HttpHosts
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.auth.OAuth1
import kaftter.producer.KafkaTwitterProducer
import mu.KotlinLogging
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

class TwitterProducer {

    private val consumerKey = System.getenv("TWITTER_CONSUMER_KEY")
    private val consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET")
    private val token = System.getenv("TWITTER_TOKEN")
    private val secret = System.getenv("TWITTER_SECRET")

    private val logger = KotlinLogging.logger {}

    fun run() {
        val messageQueue = LinkedBlockingQueue<String>(1000)
        val client = createTwitterClient(messageQueue, listOf("bitcoin", "usa", "politics", "sports"))
        client.connect()

        val kafkaProducer = KafkaTwitterProducer("localhost:9092")
        shutdownHook(client, kafkaProducer)

        while (!client.isDone) {
            try {
                val message = messageQueue.poll(5, TimeUnit.SECONDS)
                logger.info { message }
                kafkaProducer.sendMessage(message)
            } catch (e: InterruptedException) {
                logger.error { e }
                client.stop()
            }
        }

        logger.info("End of Application execution.")
    }

    /**
     * Creates the client for the twitter api.
     *
     * @return The Twitter API client.
     */
    private fun createTwitterClient(
        messageQueue: BlockingQueue<String>,
        terms: List<String>
    ): Client {
        val hosts = HttpHosts(Constants.STREAM_HOST)
        val endpoint = StatusesFilterEndpoint()

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

    /**
     * Add a shutdown hook to the producer.
     *
     * @param client Twitter client
     */
    private fun shutdownHook(
        client: Client,
        kafkaProducer: KafkaTwitterProducer
    ) {
        Runtime.getRuntime().addShutdownHook(Thread {
            logger.info("Stopping application...")
            client.stop()
            kafkaProducer.producer.close()
        })
    }

}

fun main() {
    val producer = TwitterProducer()
    producer.run()
}