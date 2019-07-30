package kaftter.producer

import mu.KotlinLogging
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

class KafkaTwitterProducer(
    private val bootstrapServers: String
) {

    private val logger = KotlinLogging.logger {}
    val producer: KafkaProducer<String, String>

    init {
        val properties = producerProperties()
        producer = KafkaProducer(properties)
    }

    /**
     * Send a message to the twitter_tweets topic.
     *
     * @param message The message to be sent.
     */
    fun sendMessage(
        message: String?
    ) {
        producer.send(ProducerRecord("twitter_tweets", null, message), Callback { _, exception ->
            if (exception != null) {
                logger.error("Something bad happened", exception)
            }
        })
    }

    /**
     * Kafka Producer properties setup.
     *
     * @return Producer properties.
     */
    private fun producerProperties(): Properties {
        val properties = Properties()

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