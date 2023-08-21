package study.community.board.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;


@Getter
@AllArgsConstructor
public class LoginMemberRequest {
    @NotNull(message = "아이디를 입력해주세요")
    String userId;
    @NotNull(message = "비밀번호를 입력해주세요")
    String password;
}
