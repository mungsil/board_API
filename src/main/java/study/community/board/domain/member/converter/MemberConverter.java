package study.community.board.domain.member.converter;

import org.springframework.data.domain.Page;
import study.community.board.domain.comment.entity.Comment;
import study.community.board.domain.member.dto.MemberRequest;
import study.community.board.domain.member.dto.MemberResponse;
import study.community.board.domain.member.entity.Member;
import study.community.board.domain.post.entity.Post;
import study.community.board.domain.comment.dto.CommentResponse;
import study.community.board.domain.post.dto.PostResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {

    public static List<MemberResponse.findMemberResultDTO> toFindMemberResultDTOList(Page<Member> memberPage) {
        return memberPage.stream()
                .map(member -> new MemberResponse.findMemberResultDTO(member.getUsername(), member.getUserId(), member.getUserRole()))
                .collect(Collectors.toList());
    }

    public static MemberResponse.findMemberResultDTO toFindMemberResultDTO(Member member) {
        return MemberResponse.findMemberResultDTO.builder()
                .userId(member.getUserId())
                .username(member.getUsername())
                .role(member.getUserRole()).build();
    }

    public static List<PostResponse.findPostResultDTO> toFindPostResultDTOList(Page<Post> postPage) {
        return postPage.stream()
                .map(post -> new PostResponse.findPostResultDTO(post))
                .collect(Collectors.toList());
    }



    public static List<CommentResponse.findCommentResultDTO> toFindCommentResultDTOList(Page<Comment> commentPage) {
        return commentPage.stream()
                .map(comment -> new CommentResponse.findCommentResultDTO(comment))
                .collect(Collectors.toList());
    }

    public static MemberRequest.ChangeMemberDTO toChangeMemberDTO(Member member) {
        return new MemberRequest.ChangeMemberDTO(member.getUsername(), member.getPassword());

    }
}
