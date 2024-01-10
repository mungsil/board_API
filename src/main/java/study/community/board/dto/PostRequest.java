package study.community.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.community.board.domain.Post;

import java.time.LocalDateTime;

public class PostRequest {
    @Getter
    @AllArgsConstructor
    public static class CreatePostDTO {
        String title;
        String content;
    }
    @Getter
    @AllArgsConstructor
    public static class ChangePostDTO {
        String title;
        String content;
    }


}
