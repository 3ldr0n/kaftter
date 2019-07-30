package kaftter.consumer

import com.google.gson.JsonParser
import mu.KotlinLogging
import org.apache.http.HttpHost
import org.elasticsearch.action.bulk.BulkRequest
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

fun extractIdFromTweet(tweetJson: String): String {
    val parser = JsonParser()
    return parser.parse(tweetJson)
        .asJsonObject
        .get("id_str")
        .asString
}

@Throws(InterruptedException::class)
fun main() {
    val logger = KotlinLogging.logger {}
    val client = createClient()

    val consumer = createConsumer("twitter_tweets")
    while (true) {
        val records = consumer.poll(Duration.ofMillis(100))
        val recordCount = records.count()

        logger.info { "Received: ${records.count()} recods"}
        val bulkRequest = BulkRequest()

        for (record in records) {
            try {
                val id = extractIdFromTweet(record.value())

                val indexRequest = IndexRequest("twitter", "tweets", id)
                    .source(record.value(), XContentType.JSON)

                bulkRequest.add(indexRequest)
            } catch (e: NullPointerException) {
                logger.warn { "Skipping bad data: ${record.value()}" }
            }
        }

        if (recordCount > 0) {
            client.bulk(bulkRequest, RequestOptions.DEFAULT)

            logger.info { "Commiting offsets..." }
            consumer.commitSync()
            logger.info { "Offsets have been committed" }
            Thread.sleep(1000)
        }

    }

}
