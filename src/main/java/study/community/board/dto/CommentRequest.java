package study.community.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentRequest {


    @AllArgsConstructor
    @Getter
    public static class CreateDTO {
        Long postId;
        String content;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor // 생성자의 인자가 하나인 경우의 역직렬화: 인자가 없는 기본 생성자가 필요하다.
    public static class ChangeDTO {
        String content;
    }
}
