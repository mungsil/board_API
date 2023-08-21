package study.community.board.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.community.board.controller.dto.CreateMemberRequest;
import study.community.board.controller.dto.LoginMemberRequest;
import study.community.board.domain.Member;
import study.community.board.security.jwt.JwtTokenUtil;
import study.community.board.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class JwtLoginApiController {

    private final AuthenticationService authenticationService;

    // 회원가입
    @PostMapping("/member")
    public String joinMember(@RequestBody CreateMemberRequest request) {

        // userId 중복 체크
        if (authenticationService.duplicateIdCheck(request.getUserId())) {
            return "아이디가 중복됩니다.";
        }
        // username 중복 체크
        if (authenticationService.duplicateNameCheck(request.getUsername())) {
            return "닉네임(사용자명)이 중복됩니다.";
        }
        // id,pw 동일성 체크
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            return "비밀번호가 일치하지 않습니다.";
        }

        authenticationService.createMember(request);
        return request.getUsername() + "님~~ 회원가입 감사합니당 헤헷";

    }

    @PostMapping("/login")
    public String login(@RequestBody LoginMemberRequest request) {
        Member loginMember = authenticationService.Login(request);
        if (loginMember == null) {
            return "일치하는 계정이 없습니다. 아이디 또는 비밀번호를 확인하여주세요.";
        }

        //로그인 -> Jwt Token 발급

        long expireTimeMs = 1000 * 60 * 60; // 유효시간 60분 설정
        String secretKey = "kim-2023-09-01-key";

        String token = JwtTokenUtil.createToken(request.getUserId(),loginMember.getUserRole(), secretKey, expireTimeMs);
        return token;
    }
}
