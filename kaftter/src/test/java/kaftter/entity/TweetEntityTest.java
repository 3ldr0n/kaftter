package kaftter.entity;

import kaftter.domain.TweetEntity;
import kaftter.domain.TweetKey;
import kaftter.vo.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TweetEntityTest {

    @Test
    public void entityToVo() {
        final String userName = "userName";
        final int userFollowers = 10;
        final String userScreenName = "userScreenName";
        final long userId = 1L;
        final String language = "en";
        final LocalDateTime time = LocalDateTime.now();
        final TweetKey key = new TweetKey(2L, time);
        final TweetEntity entity = TweetEntity.builder()
                .userName(userName)
                .userFollowers(userFollowers)
                .userScreenName(userScreenName)
                .userId(userId)
                .language(language)
                .key(key)
                .build();

        final Tweet vo = entity.toValue();

        assertThat(vo).isNotNull();
        assertThat(vo.getUser().getId()).isEqualTo(userId);
        assertThat(vo.getUser().getName()).isEqualTo(userName);
        assertThat(vo.getUser().getScreenName()).isEqualTo(userScreenName);
        assertThat(vo.getUser().getFollowers()).isEqualTo(userFollowers);
        assertThat(vo.getLanguage()).isEqualTo(language);
        assertThat(vo.getId()).isEqualTo(key.getTweetId());
    }
}
