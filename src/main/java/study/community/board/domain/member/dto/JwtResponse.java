package study.community.board.domain.member.dto;

import lombok.Getter;

public class JwtResponse {
    @Getter
    public static class CreateMemberDTO {
        String userId;
        public CreateMemberDTO(String id) {
            this.userId = id;
        }
    }
}
