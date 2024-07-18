package study.community.board.domain.member.controller;

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
import study.community.board.domain.member.entity.Member;
import study.community.board.domain.member.converter.MemberConverter;
import study.community.board.domain.member.dto.MemberRequest;
import study.community.board.domain.member.service.MemberService;
import study.community.board.global.apiPayload.ApiResponse;
import study.community.board.domain.comment.entity.Comment;
import study.community.board.domain.post.entity.Post;

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
    @GetMapping("/members")
    public ApiResponse findMember(
            @PageableDefault(size = 10, sort = {"username"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Member> memberPage = memberService.findAllMember(pageable);
        return ApiResponse.onSuccess(MemberConverter.toFindMemberResultDTOList(memberPage));
    }

    //멤버 id로 조회
    @GetMapping("members/{id}")
    public ApiResponse findMemberById(@PathVariable(name = "id") Long id) {
        try {
            Member member = memberService.findMemberById(id);
            return ApiResponse.onSuccess(MemberConverter.toFindMemberResultDTO(member));
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }


    //특정 멤버의 게시글 조회
    @GetMapping("/members/{id}/posts")
    public ApiResponse findPostsByMember(@PathVariable(name = "id") Long id,
                                           @PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> posts = memberService.findPostByMemberId(id, pageable);
        return ApiResponse.onSuccess(MemberConverter.toFindPostResultDTOList(posts));
    }

    //@GetMapping("/members/{id}/posts/{id}")

    // 멤버 id로 댓글 조회
    @GetMapping("/members/{id}/comments")
    public ApiResponse findCommentsByMemberId(@PathVariable(name = "id") Long id,
                                                     @PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Comment> comments = memberService.findCommentsByMemberID(id, pageable);
        return ApiResponse.onSuccess(MemberConverter.toFindCommentResultDTOList(comments));
    }

    //회원 정보 수정
    @PatchMapping("/members/{id}")
    public ApiResponse changeMember(
            @PathVariable(name = "id") Long id, @RequestBody @Validated MemberRequest.ChangeMemberDTO request, Authentication authentication) throws ResponseStatusException {

        if (!(id == memberService.findMemberByUserId((String) authentication.getPrincipal()).getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 계정의 수정 권한이 없습니다.");
        }

        Member updatedMember = memberService.updateInfo(request.getUsername(), request.getPassword(), id);
        return ApiResponse.onSuccess(MemberConverter.toChangeMemberDTO(updatedMember));
    }
    //회원 탈퇴
    @DeleteMapping("/members/{id}")
    public ApiResponse deleteMember(@PathVariable(name = "id") Long id, Authentication authentication) {

        Long principalID = memberService.findMemberByUserId(
                (String) authentication.getPrincipal()).getId();
        if (!(id == principalID)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 권한에 접근할 수 없습니다.");
        }
        memberService.deleteMember(id);
        return ApiResponse.onSuccess("탈퇴되었습니다.");

    }




}
