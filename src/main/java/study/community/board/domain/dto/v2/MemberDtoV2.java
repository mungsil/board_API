package study.community.board.domain.dto.v2;

import lombok.Getter;
import study.community.board.domain.Grade;
import study.community.board.domain.Member;

@Getter
public class MemberDtoV2 {

    private String username;
    private String userId;
    private Grade grade;

    public MemberDtoV2(String username,String userId,Grade grade) {
        this.username = username;
        this.userId = userId;
        this.grade = grade;
    }

}
