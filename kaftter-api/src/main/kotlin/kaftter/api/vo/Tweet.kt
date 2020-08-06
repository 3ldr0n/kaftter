package kaftter.api.vo

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class Tweet(
        val id: Long,
        val text: String,
        val user: User,
        val quoteCount: Int,
        val replyCount: Int,
        val retweetCount: Int,
        val favoriteCount: Int,
        val language: String,
        @get:JsonFormat(pattern = "YYYY-MM-DDThh:mm:ss")
        val createdAt: LocalDateTime
)
