package study.community.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.community.board.domain.Comment;
import study.community.board.domain.Post;
import study.community.board.domain.UserRole;

import java.time.LocalDateTime;

public class MemberResponse {

    @Getter
    @NoArgsConstructor
    public static class ChangeMemberResultDTO {
        String username;
        String password;
        public ChangeMemberResultDTO(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Getter
    @Builder
    public static class findMemberResultDTO {

        private String username;
        private String userId;
        private UserRole role;

        public findMemberResultDTO(String username, String userId, UserRole userRole) {
            this.username = username;
            this.userId = userId;
            this.role = userRole;
        }

    }

    //json으로의 변환을 위해서는 getter필수
    @Getter
    public static class findPostResultDTO {
        String title;
        String content;
        String username;
        LocalDateTime lastModifiedDate;
        public findPostResultDTO(Post post) {
            this.title = post.getTitle();
            this.content = post.getContent();
            this.username = post.getMember().getUsername();
            this.lastModifiedDate = post.getLastModifiedDate();
        }
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
