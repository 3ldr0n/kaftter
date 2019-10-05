package kaftter.vo

import com.google.gson.annotations.SerializedName

data class User(
    val id: Long,
    val name: String,
    @SerializedName("screen_name")
    val screenName: String
)