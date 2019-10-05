package kaftter.vo

import com.google.gson.annotations.SerializedName

data class Tweet (
    @SerializedName("created_at")
    val createdAt: String,
    val id: Long,
    val text: String,
    val user: User,
    val quoteCount: Int,
    val replyCount: Int,
    val retweetCount: Int,
    val favoriteCount: Int,
    @SerializedName("lang")
    val language: String,
    @SerializedName("timestamp_ms")
    val timestamp: String
)