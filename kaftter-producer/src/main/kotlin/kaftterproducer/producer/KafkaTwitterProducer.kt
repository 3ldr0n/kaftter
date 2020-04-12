package kaftterproducer.producer

import kaftter.tweet.Tweet
import kaftterproducer.configuration.KafkaProducerProperties
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import java.util.Objects.nonNull

private const val TWEETS_TOPIC = "stream.tweets";

class KafkaTwitterProducer(
    private val kafkaProducerProperties: KafkaProducerProperties = KafkaProducerProperties(),
    private val producer: KafkaProducer<Long, Tweet> = KafkaProducer(kafkaProducerProperties.producerProperties())
) {

    private val log = LoggerFactory.getLogger(KafkaTwitterProducer::class.java)

    /**
     * Send a message to the tweets topic.
     *
     * @param message The message to be sent.
     */
    fun sendMessage(message: Tweet) {
        val record = ProducerRecord(TWEETS_TOPIC, message.id, message)
        producer.send(record) { _, exception ->
            if (nonNull(exception)) {
                log.error("m=KafkaTwitterProducer.sendMessage, Something bad happened", exception)
            }
        }
    }

    /**
     * Closes the producer.
     */
    fun closeProducer() {
        producer.close();
    }

}