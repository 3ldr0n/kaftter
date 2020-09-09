package kaftter.consumer.converter;

import kaftter.tweet.Tweet;
import kaftter.consumer.vo.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TweetConverter {
    public kaftter.consumer.vo.Tweet convert(final Tweet tweet) {
        final var user = User.builder()
                .id(tweet.getUser().getId())
                .name(tweet.getUser().getName())
                .screenName(tweet.getUser().getScreenName())
                .followers(tweet.getUser().getFollowers())
                .build();
        return kaftter.consumer.vo.Tweet.builder()
                .id(tweet.getId())
                .text(tweet.getText())
                .user(user)
                .quoteCount(tweet.getQuoteCount())
                .favoriteCount(tweet.getFavoriteCount())
                .replyCount(tweet.getReplyCount())
                .retweetCount(tweet.getRetweetCount())
                .language(tweet.getLanguage())
                .build();
    }
}
