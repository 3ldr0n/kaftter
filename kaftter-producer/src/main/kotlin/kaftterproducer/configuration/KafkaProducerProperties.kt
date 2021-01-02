package kaftterproducer.configuration

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.LongSerializer
import java.util.Properties

private const val BOOTSTRAP_SERVERS = "localhost:29092"
private const val SCHEMA_REGISTRY_URL = "http://localhost:8081"

/**
 * Producer properties.
 */
class KafkaProducerProperties {

    /**
     * Kafka Producer properties setup.
     *
     * @return Producer properties.
     */
    fun producerProperties(): Properties {
        val properties = Properties()

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer::class.java.name)
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer::class.java.name)
        properties.setProperty(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL)
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "false")
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "0")
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Int.MAX_VALUE.toString())
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5")
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy")
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, (32 * 1024).toString())
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20")

        return properties
    }

}