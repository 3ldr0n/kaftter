package kaftterproducer.client

import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Client
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.HttpHosts
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.auth.OAuth1
import java.util.concurrent.BlockingQueue

class TwitterClient {

    private val consumerKey = System.getenv("TWITTER_CONSUMER_KEY")
    private val consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET")
    private val token = System.getenv("TWITTER_TOKEN")
    private val secret = System.getenv("TWITTER_SECRET")

    /**
     * Creates the client for the twitter api.
     *
     * @return The Twitter API client.
     */
    fun createTwitterClient(
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

}