package study.community.board.controller.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.community.board.domain.Comment;
import study.community.board.domain.Post;
import study.community.board.domain.Grade;
import study.community.board.domain.Member;
import study.community.board.domain.dto.PostDto;
import study.community.board.domain.dto.v1.CommentDtoV1;
import study.community.board.domain.dto.v2.MemberDtoV2;
import study.community.board.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 1. /logProc 작성: 여기서 로그인 정보 처리 및 토큰 발급 - jwt 공부 - 나중에 구현합니다. 현재 이거 빼고 코드 작성 완료
 * 2. /join(회원가입) 작성
 * 3. /board 작성 : 여기서 db에 저장되어 있는 post의 제목, 글쓴이, 날짜 리스트로 반환
 * 4. /posts/{id} : 여기서 id의 post 제목, 내용, 날짜 반환
 */

/**
 * < 주요 문제 사항 >
 * 1. 페이징하기
 * 2. 로그인 기능 구현
 * 3. 위 과정에서 필요한 queryDSL
 * 4. 검색 기능(사용자명, 내용, 제목) 구현은 따로 컨트롤러를 만들어야하나?
 * 5. Page에서는 dto로 못받아오는데. 그럼 이거 lazy 로딩 설정 그대로 이어져서 성능 최적화 못하는 건가? 내 예상으로는
 *  Member로 받아와도 lazy니 쿼리가 날라가지 않을거고, dto로 변환하는 과정에서 성능 최적화 될 거라고 생각했는데(한꺼번에 1000개씩 가져오는)
 *  쿼리 날라가는 거 보니까 안되는 것 같기도 하다. 안된다는 가정 하에 선택지는 다음과 같다.
 *  1. queryDSL로 성능 최적화
 *  2. 그냥 그대로 사용했다가 queryDSL 배우고 refactoring
 *
 */

/**
 * 작성한 게시글을 json으로 반환
 * 작성한 댓글을 json으로 반환
 */

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 로그인

    //멤버 전체 조회
    @GetMapping("/members")
    public List<MemberDtoV2> findMember(@PageableDefault(size = 10, sort = {"username"}, direction = Sort.Direction.DESC) Pageable pageable) {
        List<MemberDtoV2> memberList = memberService.findAllMember(pageable).stream()
                .map(member -> new MemberDtoV2(member.getUsername(),member.getUserId(),member.getGrade()))
                .collect(Collectors.toList());
        return memberList;
    }

    //특정 멤버 조회- 댓글과 게시글은 제외한 정보 반환 - id로
    @GetMapping("members/{id}")
    public MemberDtoV2 findMemberById(@PathVariable(name = "id") Long id) {
        return memberService.findMember(id);
    }

    //특정 멤버의 게시글 조회
    @GetMapping("/members/{id}/posts")
    public List<PostDto> findPostsByMember(@PathVariable(name = "id") Long id,
                                           @PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> postById = memberService.findPostByMemberId(id, pageable);
        List<PostDto> collect = postById.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());
        return collect;
    }


    // 특정 멤버의 댓글 조회
    @GetMapping("/members/{id}/comments")
    public List<CommentDtoV1> findCommentsByMemberId(@PathVariable(name = "id") Long id,
                                                     @PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Comment> commentsByMemberID = memberService.findCommentsByMemberID(id, pageable);
        List<CommentDtoV1> collect = commentsByMemberID.stream().map(comment -> new CommentDtoV1(comment)).collect(Collectors.toList());
        return collect;
    }

    //특정 멤버 조회 - 멤버로 게시글을 조회하는 건데



    //회원 가입
    //@Validated는 뭐지?
    @PostMapping("/members")
    public CreateMemberResponse join(@RequestBody @Validated CreateMemberRequest request) {

        Member member = Member.createMember(
                request.getUsername(), request.getUserId(), request.getUserPassword(), Grade.C);
        memberService.saveMember(member);

        return new CreateMemberResponse(member.getId());

    }

    //회원 탈퇴

    //로그아웃







    @Getter
    static class CreateMemberRequest {
        String userId;
        String userPassword;
        String username;

    }

    @Getter
    static class CreateMemberResponse {
        Long id;
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    /*
    //페이징 기능 추가
    @GetMapping("/posts")
    public Member memberInfo() {

        return memberService.findMember(id);
    }

    //페이징 기능 추가
    @GetMapping("/members/comments")
    public Member memberInfo(@PathVariable(name = "id") Long id) {

        return memberService.findMember(id);
    }*/
}
