package study.community.board.controller.api;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import study.community.board.domain.Comment;
import study.community.board.domain.Member;
import study.community.board.domain.Post;
import study.community.board.domain.dto.PostDto;
import study.community.board.domain.dto.v1.CommentDtoV1;
import study.community.board.domain.dto.v2.MemberDtoV2;
import study.community.board.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

/*
- 게시글 삭제 시 댓글 삭제 문제
- 검색
- 로그아웃 기능
 */

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    //멤버 전체 조회
    @GetMapping("/members/list")
    public Result findMember(
            @PageableDefault(size = 10, sort = {"username"}, direction = Sort.Direction.DESC) Pageable pageable) {
        List<MemberDtoV2> memberList = memberService.findAllMember(pageable).stream()
                .map(member -> new MemberDtoV2(member.getUsername(), member.getUserId(), member.getUserRole()))
                .collect(Collectors.toList());
        return new Result(memberList);
    }

    //멤버 id로 조회
    @GetMapping("members/{id}")
    public Result findMemberById(@PathVariable(name = "id") Long id) {
        try {
            Member member = memberService.findMemberById(id);
            return new Result(new MemberDtoV2(member.getUsername(), member.getUserId(), member.getUserRole()));
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }


    //특정 멤버의 게시글 조회
    @GetMapping("/members/{id}/posts")
    public Result findPostsByMember(@PathVariable(name = "id") Long id,
                                           @PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> posts = memberService.findPostByMemberId(id, pageable);
        List<PostDto> postDtoList = posts.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());
        return new Result(postDtoList);
    }

    //@GetMapping("/members/{id}/posts/{id}")

    // 멤버 id로 댓글 조회
    @GetMapping("/members/{id}/comments")
    public Result findCommentsByMemberId(@PathVariable(name = "id") Long id,
                                                     @PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Comment> comments = memberService.findCommentsByMemberID(id, pageable);
        List<CommentDtoV1> commentDtoList = comments.stream().map(comment -> new CommentDtoV1(comment)).collect(Collectors.toList());
        return new Result(commentDtoList);
    }

    //회원 정보 수정
    @PostMapping("/members/{id}")
    public Result changeMember(
            @PathVariable(name = "id") Long id, @RequestBody @Validated changeMemberInfoRequest request, Authentication authentication) throws ResponseStatusException {

        if (!(id == memberService.findMemberByUserId((String) authentication.getPrincipal()).getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 계정의 수정 권한이 없습니다.");
        }
        Member updatedMember = memberService.updateInfo(request.getUsername(), request.getPassword(), id);
        return new Result(new ChangeMemberInfoResponse(updatedMember.getUsername(), updatedMember.getPassword()));
    }
    //회원 탈퇴
    @DeleteMapping("/members/{id}")
    public String deleteMember(@PathVariable(name = "id") Long id, Authentication authentication) {

        Long principalID = memberService.findMemberByUserId(
                (String) authentication.getPrincipal()).getId();
        if (!(id == principalID)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 권한에 접근할 수 없습니다.");
        }
        memberService.deleteMember(id);
        return "탈퇴되었습니다.";

    }
    @Getter
    @NoArgsConstructor
    private static class changeMemberInfoRequest {
        String username;
        String password;
        public changeMemberInfoRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Getter
    @NoArgsConstructor
    private static class ChangeMemberInfoResponse {
        String username;
        String password;
        public ChangeMemberInfoResponse(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Result<T> {
        private T data;
    }
}
