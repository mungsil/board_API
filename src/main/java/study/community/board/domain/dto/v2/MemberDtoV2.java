package study.community.board.domain.dto.v2;

import lombok.Getter;
import study.community.board.domain.UserRole;

@Getter
public class MemberDtoV2 {

    private String username;
    private String userId;
    private UserRole userRole;

    public MemberDtoV2(String username, String userId, UserRole userRole) {
        this.username = username;
        this.userId = userId;
        this.userRole = userRole;
    }

}
