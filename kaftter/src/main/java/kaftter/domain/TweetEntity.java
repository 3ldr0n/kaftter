package kaftter.domain;

import kaftter.vo.Tweet;
import kaftter.vo.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Table("tweets")
@Getter
@Setter
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