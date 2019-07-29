package kaftter.consumer

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.Properties

fun consumerProperties(): Properties {
    val properties = Properties()
    val bootstrapServers = "localhost:9092"
    val groupId = "elasticsearch-consumer"

    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    return properties
}

fun createConsumer(topic: String): KafkaConsumer<String, String> {
    val properties = consumerProperties()
    val consumer = KafkaConsumer<String, String>(properties)
    consumer.subscribe(listOf(topic))

    return consumer
}

