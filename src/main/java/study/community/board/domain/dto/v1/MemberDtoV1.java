package study.community.board.domain.dto.v1;


import lombok.Getter;
import study.community.board.domain.UserRole;
import study.community.board.domain.Member;
import study.community.board.domain.dto.PostDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberDtoV1 {

    private String username;
    private String userId;
    private UserRole userRole;
    private List<CommentDtoV1> commentDtoList = new ArrayList<>();
    private List<PostDto> postDtoList = new ArrayList<>();

   public MemberDtoV1(Member member) {
       this.username =member.getUsername();
       this.userId =member.getUserId();
       this.userRole =member.getUserRole();
       this.commentDtoList = member.getCommentList().stream()
               .map(comment -> new CommentDtoV1(comment))
               .collect(Collectors.toList());
       this.postDtoList=member.getPostList().stream()
               .map(post -> new PostDto(post))
               .collect(Collectors.toList());
   }

}
