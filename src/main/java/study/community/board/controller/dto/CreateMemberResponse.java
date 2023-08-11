package study.community.board.controller.dto;

import lombok.Getter;

@Getter
public class CreateMemberResponse {
    String userId;
    public CreateMemberResponse(String id) {
        this.userId = id;
    }
}
