package kaftter.api.converter

import kaftter.api.domain.SummarizedTweetEntity
import kaftter.api.domain.TweetEntity
import kaftter.api.domain.TweetKey
import kaftter.api.vo.Tweet
import kaftter.api.vo.TweetSummary
import java.time.LocalDate

fun convert(summarizedTweetEntity: SummarizedTweetEntity): TweetSummary {
    return TweetSummary(
            userId = summarizedTweetEntity.userId,
            userName = summarizedTweetEntity.userName,
            favoriteCount = summarizedTweetEntity.favoriteCount,
            quoteCount = summarizedTweetEntity.quoteCount,
            replyCount = summarizedTweetEntity.replyCount,
            retweetCount = summarizedTweetEntity.retweetCount
    )
}

fun convert(tweet: Tweet): TweetEntity {
    val tweetKey = TweetKey(
            tweetId = tweet.id,
            userId = tweet.user.id,
            createdAt = LocalDate.now()
    )
    return TweetEntity(
            key = tweetKey,
            text = tweet.text,
            quoteCount = tweet.quoteCount,
            replyCount = tweet.replyCount,
            retweetCount = tweet.retweetCount,
            favoriteCount = tweet.favoriteCount,
            language = tweet.language,
            userName = tweet.user.name,
            userScreenName = tweet.user.screenName,
            userFollowers = tweet.user.followers
    )
}
