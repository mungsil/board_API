package study.community.board.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.community.board.domain.member.entity.UserRole;

public class MemberResponse {

    @Getter
    @NoArgsConstructor
    public static class ChangeMemberResultDTO {
        String username;
        String password;
        public ChangeMemberResultDTO(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Getter
    @Builder
    public static class findMemberResultDTO {

        private String username;
        private String userId;
        private UserRole role;

        public findMemberResultDTO(String username, String userId, UserRole userRole) {
            this.username = username;
            this.userId = userId;
            this.role = userRole;
        }

    }



}
