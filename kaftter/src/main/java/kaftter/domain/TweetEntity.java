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

    public void setKey(final TweetKey key) {
        this.key = key;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setQuoteCount(final int quoteCount) {
        this.quoteCount = quoteCount;
    }

    public void setReplyCount(final int replyCount) {
        this.replyCount = replyCount;
    }

    public void setRetweetCount(final int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public void setFavoriteCount(final int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
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