package kaftter.elasticsearch

import mu.KotlinLogging
import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType


fun createClient(): RestHighLevelClient {
    val hostname = "localhost"

    val builder = RestClient.builder(
        HttpHost(hostname, 9200, "http"))

    return RestHighLevelClient(builder)
}

fun main() {
    val logger = KotlinLogging.logger {}
    val client = createClient()

    val json = "{\"foo\": \"bar\"}"

    val indexRequest = IndexRequest("twitter", "tweets")
        .source(json, XContentType.JSON)

    val response = client.index(indexRequest, RequestOptions.DEFAULT)
    val id = response.id

    logger.info { id }

    client.close()
}