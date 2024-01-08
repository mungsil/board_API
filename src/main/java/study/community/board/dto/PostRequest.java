package study.community.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
