package kaftter.consumer.factory;

import kaftter.consumer.vo.Tweet;
import kaftter.consumer.vo.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TweetFactory {

    public kaftter.tweet.Tweet mockTweetEvent() {
        final var user = new kaftter.tweet.User(1L, "name", "screenName", 1);
        return new kaftter.tweet.Tweet(1L, "text", user, 1, 1, 1, 1, "en");
    }

    public Tweet mockTweet() {
        final var user = new User(1L, "name", "screenName", 1);
        return new Tweet(1L, "text", user, 1, 1, 1, 1, "en");
    }

}
