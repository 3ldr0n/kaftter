package kaftter.consumer.configuration;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {
    public static final String TWEET_CONSUMER_CONTAINER_FACTORY = "tweetConsumerContainerFactory";

    private final String groupId;
    private final int concurrency;
    private final String offsetReset;
    private final String sessionTimeout;
    private final String bootstrapServers;
    private final String schemaRegistryUrl;

    public KafkaConsumerConfiguration(@Value("${spring.kafka.consumer.group-id}") final String groupId,
                                      @Value("${spring.kafka.consumer.concurrency}") final int concurrency,
                                      @Value("${spring.kafka.consumer.auto-offset-reset}") final String offsetReset,
                                      @Value("${spring.kafka.consumer.session-timeout}") final String sessionTimeout,
                                      @Value("${spring.kafka.bootstrap-servers}") final String bootstrapServers,
                                      @Value("${spring.kafka.schema-registry}") final String schemaRegistryUrl) {
        this.groupId = groupId;
        this.concurrency = concurrency;
        this.offsetReset = offsetReset;
        this.sessionTimeout = sessionTimeout;
        this.bootstrapServers = bootstrapServers;
        this.schemaRegistryUrl = schemaRegistryUrl;
    }

    @Bean(TWEET_CONSUMER_CONTAINER_FACTORY)
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> tweetConsumerContainerFactory() {
        final var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        final var consumerProperties = getConsumerProperties();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(consumerProperties));
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    private Map<String, Object> getConsumerProperties() {
        final var properties = new HashMap<String, Object>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return properties;
    }

}
