package kaftter

import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Client
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.HttpHosts
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.auth.OAuth1
import mu.KotlinLogging
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties
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

        val kafkaProducer = createKafkaProducer()

        Runtime.getRuntime().addShutdownHook(Thread {
            logger.info("Stopping application...")
            client.stop()
            kafkaProducer.close()
        })

        while (!client.isDone) {
            try {
                val message = messageQueue.poll(5, TimeUnit.SECONDS)
                logger.info(message)
                kafkaProducer.send(ProducerRecord("twitter_tweets", null, message), Callback { _, exception ->
                    if (exception != null) {
                        logger.error("Something bad happened", exception)
                    }
                })
            } catch (e: InterruptedException) {
                logger.error { e }
                client.stop()
            }
        }

        logger.info("End of Application execution.")
    }

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

    private fun createKafkaProducer(): KafkaProducer<String, String> {
        val properties = producerProperties()

        return KafkaProducer(properties)
    }

    /**
     * Kafka Producer properties setup.
     *
     * @return Producer properties.
     */
    private fun producerProperties(): Properties {
        val properties = Properties()
        val bootstrapServers = "localhost:9092"
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true")
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all")
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Int.MAX_VALUE.toString())
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5")
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy")
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, (32 * 1024).toString())
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20")

        return properties
    }

}
