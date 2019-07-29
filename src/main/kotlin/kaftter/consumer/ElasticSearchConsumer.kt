package kaftter.consumer

import mu.KotlinLogging
import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import java.time.Duration

fun createClient(): RestHighLevelClient {
    val hostname = "localhost"

    val builder = RestClient.builder(
        HttpHost(hostname, 9200, "http"))

    return RestHighLevelClient(builder)
}

@Throws(InterruptedException::class)
fun main() {
    val logger = KotlinLogging.logger {}
    val client = createClient()

    val consumer = createConsumer("twitter_tweets")
    while (true) {
        val records = consumer.poll(Duration.ofMillis(100))

        for (record in records) {
            val indexRequest = IndexRequest("twitter", "tweets")
                .source(record.value(), XContentType.JSON)

            val response = client.index(indexRequest, RequestOptions.DEFAULT)
            val id = response.id

            logger.info { id }
            Thread.sleep(1000)
        }

    }

}