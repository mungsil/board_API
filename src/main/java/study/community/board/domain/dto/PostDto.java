package study.community.board.domain.dto;

import lombok.Getter;
import study.community.board.domain.Post;

import java.time.LocalDateTime;

//json으로의 변환을 위해서는 getter필수
@Getter
public class PostDto {
    String title;
    String content;
    String username;
    LocalDateTime lastModifiedDate;
    public PostDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getMember().getUsername();
        this.lastModifiedDate = post.getLastModifiedDate();
    }
}
