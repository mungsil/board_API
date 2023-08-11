package study.community.board.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.community.board.domain.Member;
import study.community.board.domain.UserRole;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CreateMemberRequest { //JoinMemberRequest

    @NotBlank(message = "id는 필수 입력값입니다")
    String userId;

    @NotBlank(message = "비밀번호를 입력하세요")
    String userPassword;
    String passwordCheck;

    @NotBlank(message = "사용자명을 입력하세요")
    String username;

    public Member toEntity(String encodedPassword) {
        return Member.createMember(username, userId, encodedPassword, UserRole.USER);
    }

}

