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


   /*
   아래처럼 하면 자동으로 누군가가 Member를 찾아서 주입해줄거라는 상상은 금물. ^^
   public MemberDto(String username, String userId, Grade grade, List<Comment> comments, List<Post> posts) {
        this.username = username;
        this.userId = userId;
        this.grade = grade;
        this.commentDtoList = comments.stream()
                .map(comment -> new CommentDto(comment.getContent(),comment.getCreatedDate()))
                .collect(Collectors.toList());
        this.postDtoList=posts.stream()
                .map(post -> new PostDto(post.getTitle(), post.getContent(),post.getCreatedDate()))
                .collect(Collectors.toList());
    }
*/
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
