package study.community.board.domain.dto.v2;

import lombok.Getter;
import study.community.board.domain.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentDtoV2 {
    String content;
    String username;
    LocalDateTime lastModifiedDate;

    public CommentDtoV2(Comment comment) {
        this.content = comment.getContent();
        this.username = comment.getMember().getUsername();
        this.lastModifiedDate = comment.getLastModifiedDate();
    }
}