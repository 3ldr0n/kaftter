package kaftter.consumer.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {
    private Long id;
    private String text;
    private User user;
    private int quoteCount;
    private int replyCount;
    private int retweetCount;
    private int favoriteCount;
    private String language;
}