package study.community.board.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequest {
    @Getter
    @NoArgsConstructor
    public static class ChangeMemberDTO {
        String username;
        String password;
        public ChangeMemberDTO(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
