package study.community.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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


}
