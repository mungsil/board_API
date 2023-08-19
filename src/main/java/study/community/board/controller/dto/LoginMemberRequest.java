package study.community.board.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
여기는 머 id랑 password @NotNull같은거 안걸어줘도 돼요?
 */
@Getter
@AllArgsConstructor
public class LoginMemberRequest {
    String userId;
    String password;
}
