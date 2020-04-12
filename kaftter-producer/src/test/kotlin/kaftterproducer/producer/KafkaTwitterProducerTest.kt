package kaftterproducer.producer

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kaftter.tweet.Tweet
import kaftterproducer.configuration.KafkaProducerProperties
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.RecordMetadata
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Properties
import java.util.concurrent.Future

class KafkaTwitterProducerTest {
    @RelaxedMockK
    private lateinit var kafkaProducerProperties: KafkaProducerProperties

    @RelaxedMockK
    private lateinit var producer: KafkaProducer<Long, Tweet>

    private lateinit var kafkaTwitterProducer: KafkaTwitterProducer

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        val properties: Properties = mockk()
        every { kafkaProducerProperties.producerProperties() } returns properties
        kafkaTwitterProducer = KafkaTwitterProducer(kafkaProducerProperties, producer)
    }

    @Test
    fun testSendMessage() {
        val futureMock: Future<RecordMetadata> = mockk()
        val tweetMock: Tweet = mockk()
        val callback = slot<Callback>()
        every { tweetMock.id } returns 1
        every { producer.send(any(), capture(callback)) } returns futureMock

        kafkaTwitterProducer.sendMessage(tweetMock)

        verify { producer.send(any()) }
    }
}