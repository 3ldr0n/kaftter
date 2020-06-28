package kaftter.domain;

/**
 * Headers used to export tweets to CSV.
 */
public enum TweetCSVHeaders {
    ID,
    TEXT,
    QUOTE_COUNT,
    REPLY_COUNT,
    RETWEET_COUNT,
    FAVORITE_COUNT,
    LANGUAGE,
    TIMESTAMP,
    USER_ID,
    USER_NAME,
    USER_SCREEN_NAME,
    USER_FOLLOWERS
}