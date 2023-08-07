package study.community.board.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.community.board.controller.dto.MemberDto;
import study.community.board.domain.Grade;
import study.community.board.domain.Member;
import study.community.board.service.MemberService;

import java.util.Optional;

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
 */

/**
 * 작성한 게시글을 json으로 반환
 * 작성한 댓글을 json으로 반환
 */

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/test")
    public String test() {
        return "test message";
    }

    @GetMapping("/members/{username}")
    public MemberDto findByUsername(@PathVariable(name = "username") String username) {
        MemberDto memberDto = memberService.findMemberDto(username);
        return memberDto;
    }

    /*@GetMapping("/myposts")
    public Member memberInfo() {

        return memberService.findMember(id);
    }

    @GetMapping("/mycomments")
    public Member memberInfo(@PathVariable(name = "id") Long id) {

        return memberService.findMember(id);
    }*/
}
