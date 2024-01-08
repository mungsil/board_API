package study.community.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.community.board.domain.Member;
import study.community.board.domain.UserRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class JwtRequest {
    @Getter
    @AllArgsConstructor
    public static class CreateMemberDTO { //JoinMemberRequest

        @NotBlank(message = "id는 필수 입력값입니다")
        String userId;

        @NotBlank(message = "비밀번호를 입력하세요")
        String password;
        String passwordCheck;

        @NotBlank(message = "사용자명을 입력하세요")
        String username;

        public Member toEntity(String encodedPassword) {
            return Member.createMember(username, userId, encodedPassword, UserRole.ROLE_USER);
        }

    }

    @Getter
    @AllArgsConstructor
    public static class LoginMemberDTO {
        @NotNull(message = "아이디를 입력해주세요")
        String userId;
        @NotNull(message = "비밀번호를 입력해주세요")
        String password;
    }

}
