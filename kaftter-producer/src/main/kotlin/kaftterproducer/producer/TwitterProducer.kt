package kaftterproducer.producer

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.twitter.hbc.core.Client
import kaftterproducer.client.TwitterClient
import kaftterproducer.vo.TweetVO
import org.slf4j.LoggerFactory
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

class TwitterProducer(
    private val twitterClient: TwitterClient = TwitterClient(),
    private val kafkaProducer: KafkaTwitterProducer = KafkaTwitterProducer()
) {

    private val logger = LoggerFactory.getLogger(TwitterProducer::class.java)

    fun run() {
        val messageQueue = LinkedBlockingQueue<String>(1000)
        val client = twitterClient.createTwitterClient(messageQueue, listOf("bitcoin", "usa", "politics", "sports"))
        client.connect()

        shutdownHook(client)

        while (!client.isDone) {
            try {
                val message = messageQueue.poll(5, TimeUnit.SECONDS)
                logger.info(message)
                message?.let {
                    sendMessage(it)
                }
            } catch (e: InterruptedException) {
                logger.error("m=TwitterProducer.run", e)
                client.stop()
            }
        }

        logger.info("m=TwitterProducer.run, End of Application execution.")
    }

    /**
     * Add a shutdown hook to the producer.
     *
     * @param client Twitter client
     */
    private fun shutdownHook(client: Client) {
        Runtime.getRuntime().addShutdownHook(Thread {
            logger.info("m=TwitterProducer.shutdownHook, Stopping application...")
            client.stop()
            kafkaProducer.closeProducer()
        })
    }

    /**
     * Sends a message to kafka.
     *
     * @param message Message to be sent
     */
    private fun sendMessage(message: String) {
        val mapper = jacksonObjectMapper()
        val tweetVO = mapper.readValue<TweetVO>(message)
        val tweet = tweetVO.toAvro()
        kafkaProducer.sendMessage(tweet)
    }

}