package kaftterproducer.vo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kaftter.tweet.User

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserVO(
    private val id: Long,
    private val name: String,
    @JsonProperty("screen_name")
    private val screenName: String,
    @JsonProperty("followers_count")
    private val followers: Long
) {
    fun toAvro(): User {
        return User(id, name, screenName, followers)
    }
}