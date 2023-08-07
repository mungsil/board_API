package study.community.board.controller.dto;


import lombok.Getter;
import study.community.board.domain.Grade;

@Getter
public class MemberDto {

    private String username;
    private String userId;
    private Grade grade;
    //private List<CommentDto> commentDtoList;


    public MemberDto(String username, String userId, Grade grade) {
        this.username = username;
        this.userId = userId;
        this.grade = grade;
    }
}
