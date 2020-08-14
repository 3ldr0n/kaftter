package kaftter.api.resource

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kaftter.api.exception.UserNotFoundException
import kaftter.api.service.TweetService
import kaftter.api.vo.Tweet
import kaftter.api.vo.TweetSummary
import kaftter.api.vo.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.cassandra.CassandraUnauthorizedException
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.put
import java.time.LocalDate

@WebMvcTest
@ExtendWith(MockKExtension::class)
class TweetResourceTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var tweetService: TweetService

    @Test
    fun `test search summary for registered user should return ok with tweet summary`() {
        val userId = 1L
        val expectedSummary = TweetSummary(
                userId = userId,
                userName = "userName",
                favoriteCount = 1,
                quoteCount = 1,
                replyCount = 1,
                retweetCount = 1
        )
        every { tweetService.search(userId) } returns expectedSummary
        val result = mockMvc.get("/tweets/$userId") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk }
            content { contentType(MediaType.APPLICATION_JSON) }
        }.andReturn()

        assertThat(result.response.errorMessage).isNull()
        val tweetSummary = jacksonObjectMapper().readValue<TweetSummary>(result.response.contentAsString)
        assertThat(tweetSummary).isEqualTo(expectedSummary)
        verify { tweetService.search(userId) }
    }

    @Test
    fun `test search summary for unregistered user should return not found`() {
        val userId = 1L
        every { tweetService.search(userId) } throws UserNotFoundException(userId)
        val result = mockMvc.get("/tweets/$userId") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound }
        }.andReturn()

        assertThat(result.response.errorMessage).isNull()
        assertThat(result.response.contentAsString).isEmpty()
        verify { tweetService.search(userId) }
    }

    @Test
    fun `test register tweet should save tweet`() {
        val tweet = mockTweet()
        every { tweetService.save(any()) } returns Unit
        val result = mockMvc.put("/tweets") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(tweet)
        }.andExpect {
            status { isOk }
        }.andReturn()

        assertThat(result.response.errorMessage).isNull()

        verify { tweetService.save(any()) }
    }

    @Test
    fun `test register tweet with exception writing to database should return internal server error`() {
        val tweet = mockTweet()
        every { tweetService.save(any()) } throws CassandraUnauthorizedException("", Exception())
        val result = mockMvc.put("/tweets") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(tweet)
        }.andExpect {
            status { isInternalServerError }
        }.andReturn()

        assertThat(result.response.errorMessage).isNull()

        verify { tweetService.save(any()) }
    }

    private fun mockTweet(): Tweet {
        return Tweet(
                id = 1L,
                text = "test",
                favoriteCount = 1,
                replyCount = 1,
                quoteCount = 2,
                retweetCount = 0,
                language = "en",
                createdAt = LocalDate.now(),
                user = User(
                        id = 2,
                        followers = 0,
                        name = "none",
                        screenName = "none"
                )
        )
    }

}
