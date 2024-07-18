package study.community.board.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import study.community.board.domain.comment.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponse {

    @Getter @Builder
    @AllArgsConstructor
    public static class CreateResultDTO {
        Long commentId;
        String content;
        String createdBy;
        LocalDateTime lastModifiedDate;
    }
    @Getter  @Builder
    @AllArgsConstructor
    public static class ChangeResultDTO {
        String content;
    }

    @Getter
    public static class findCommentResultDTO {
        String content;
        Long postId;
        String postName;
        String username;
        LocalDateTime lastModifiedDate;
        public findCommentResultDTO(Comment comment) {
            this.content = comment.getContent();
            this.postId = comment.getPost().getId();
            this.postName = comment.getPost().getTitle();
            this.username = comment.getMember().getUsername();
            this.lastModifiedDate = comment.getLastModifiedDate();
        }
    }

}
