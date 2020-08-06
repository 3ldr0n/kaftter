package kaftter.api.vo

data class User(
        val id: Long,
        val name: String,
        val screenName: String,
        val followers: Int
)
