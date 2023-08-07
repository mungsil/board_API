package study.community.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import study.community.board.service.MemberService;

/**
 * 내가 쓴 댓글과 게시글을 볼 수 있는 기능
 * 댓글과 게시글 삭제 기능
 *
 */


@Controller
@RequiredArgsConstructor
@RequestMapping("/") //homeController인가?
public class MemberController {
    private final MemberService memberService;

}
