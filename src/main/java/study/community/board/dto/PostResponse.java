package study.community.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public static class findPostResultDTO {
        String title;
        String content;
        String username;
        LocalDateTime lastModifiedDate;
        List<commentResultDTO> commentDtoList = new ArrayList<>();
        public findPostResultDTO(Post post, Page<Comment> commentByPostId) {
            this.title = post.getTitle();
            this.content = post.getContent();
            this.username = post.getMember().getUsername();
            this.lastModifiedDate = post.getLastModifiedDate();
            //PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
            this.commentDtoList = commentByPostId
                    .stream()
                    .map(comment -> new commentResultDTO(comment))
                    .collect(Collectors.toList());
        }

    }

    @Getter
    public static class commentResultDTO {
        String content;
        String username;
        LocalDateTime lastModifiedDate;

        public commentResultDTO(Comment comment) {
            this.content = comment.getContent();
            this.username = comment.getMember().getUsername();
            this.lastModifiedDate = comment.getLastModifiedDate();
        }
    }
}
