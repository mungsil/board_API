package study.community.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.community.board.domain.Comment;
import study.community.board.domain.Post;
import study.community.board.service.CommentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostResponse {

    private final CommentService commentService;
    @Getter
    @AllArgsConstructor
    public static class CreatePostResultDTO {
        String title;
        String content;
        String username;
    }
    @Getter
    @AllArgsConstructor
    public static class ChangePostResultDTO {
        String title;
        String content;
        String createdBy;
    }

    @Getter
    public static class FindPostWithCommentResultDTO {
        String title;
        String content;
        String username;
        LocalDateTime lastModifiedDate;
        List<CommentResultDTO> commentDtoList = new ArrayList<>();
        public FindPostWithCommentResultDTO(Post post, List<Comment> commentList) {
            this.title = post.getTitle();
            this.content = post.getContent();
            this.username = post.getMember().getUsername();
            this.lastModifiedDate = post.getLastModifiedDate();
            this.commentDtoList = commentList.stream()
                    .map(comment -> new PostResponse.CommentResultDTO(comment))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    public static class CommentResultDTO {
        String content;
        String username;
        LocalDateTime lastModifiedDate;

        public CommentResultDTO(Comment comment) {
            this.content = comment.getContent();
            this.username = comment.getMember().getUsername();
            this.lastModifiedDate = comment.getLastModifiedDate();
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
}
