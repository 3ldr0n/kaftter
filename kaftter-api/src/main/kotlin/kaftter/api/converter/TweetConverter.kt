package kaftter.api.converter

import kaftter.api.domain.SummarizedTweetEntity
import kaftter.api.domain.TweetEntity
import kaftter.api.domain.TweetKey
import kaftter.api.vo.Tweet
import kaftter.api.vo.TweetSummary

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
    val tweetKey = TweetKey(tweet.id, tweet.user.id)
    return TweetEntity(
            key = tweetKey,
            text = tweet.text,
            quoteCount = tweet.quoteCount,
            replyCount = tweet.replyCount,
            retweetCount = tweet.retweetCount,
            favoriteCount = tweet.favoriteCount,
            language = tweet.language,
            createdAt = tweet.createdAt,
            userName = tweet.user.name,
            userScreenName = tweet.user.screenName,
            userFollowers = tweet.user.followers
    )
}
