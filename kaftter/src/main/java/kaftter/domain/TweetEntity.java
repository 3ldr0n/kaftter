package kaftter.domain;

import java.io.Serializable;
import kaftter.vo.Tweet;
import kaftter.vo.User;
import lombok.Builder;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("tweets")
@Builder
public class TweetEntity implements Serializable {
    @PrimaryKey
    private TweetKey key;

    private String text;

    @Column("quote_count")
    private int quoteCount;

    @Column("reply_count")
    private int replyCount;

    @Column("retweet_count")
    private int retweetCount;

    @Column("favorite_count")
    private int favoriteCount;

    private String language;

    private Long timestamp;

    @Column("user_id")
    private Long userId;

    @Column("user_name")
    private String userName;

    @Column("user_screen_name")
    private String userScreenName;

    @Column("user_followers_count")
    private Long userFollowers;

    public TweetKey getKey() {
        return key;
    }

    public void setKey(final TweetKey key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public int getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(final int quoteCount) {
        this.quoteCount = quoteCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(final int replyCount) {
        this.replyCount = replyCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(final int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(final int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(final String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public Long getUserFollowers() {
        return userFollowers;
    }

    public void setUserFollowers(final Long userFollowers) {
        this.userFollowers = userFollowers;
    }

    public Tweet toValue() {
        final var user = new User(userId, userName, userScreenName, userFollowers);
        return Tweet.builder()
                .id(key.getTweetId())
                .text(text)
                .quoteCount(quoteCount)
                .replyCount(replyCount)
                .retweetCount(retweetCount)
                .favoriteCount(favoriteCount)
                .language(language)
                .timestamp(timestamp)
                .user(user)
                .build();
    }
}