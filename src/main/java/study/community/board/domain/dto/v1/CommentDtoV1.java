package study.community.board.domain.dto.v1;

import lombok.Getter;
import study.community.board.domain.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentDtoV1 {
    String content;
    String postName;
    String username;
    LocalDateTime lastModifiedDate;
    public CommentDtoV1(Comment comment) {
        this.content = comment.getContent();
        this.postName = comment.getPost().getTitle();
        this.username = comment.getMember().getUsername();
        this.lastModifiedDate = comment.getLastModifiedDate();
    }
}
