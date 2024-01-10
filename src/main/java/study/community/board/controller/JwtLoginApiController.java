package study.community.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.community.board.apiPayload.ApiResponse;
import study.community.board.apiPayload.code.BaseErrorCode;
import study.community.board.apiPayload.code.ErrorStatus;
import study.community.board.domain.Member;
import study.community.board.dto.JwtRequest;
import study.community.board.security.jwt.JwtTokenUtil;
import study.community.board.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class JwtLoginApiController {

    private final AuthenticationService authenticationService;

    // 회원가입
    @PostMapping("/members")
    public ApiResponse joinMember(@RequestBody JwtRequest.CreateMemberDTO request) {

        // userId 중복 체크
        if (authenticationService.duplicateIdCheck(request.getUserId())) {
            return ApiResponse.onFailure(ErrorStatus.MEMBER_BAD_REQUEST.getCode(),"아이디가 중복됩니다.","실패");
        }
        // username 중복 체크
        if (authenticationService.duplicateNameCheck(request.getUsername())) {
            return ApiResponse.onFailure(ErrorStatus.MEMBER_BAD_REQUEST.getCode(),"닉네임이 중복됩니다.","실패");
        }
        // pw 동일성 체크
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            return ApiResponse.onFailure(ErrorStatus.MEMBER_BAD_REQUEST.getCode(),"비밀번호가 일치하지 않습니다.","실패");

        }

        authenticationService.createMember(request);
        return ApiResponse.onSuccess(request.getUsername() + "님~~ 회원가입 감사합니다. (˘❥˘)");

    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody JwtRequest.LoginMemberDTO reqeust) {
        Member loginMember = authenticationService.Login(reqeust);
        if (loginMember == null) {

            return ApiResponse.onFailure(ErrorStatus.MEMBER_BAD_REQUEST.getCode(),"일치하는 계정이 없습니다. 아이디 또는 비밀번호를 확인하여주세요.","실패");

        }

        //로그인 -> Jwt Token 발급

        long expireTimeMs = 1000 * 60 * 60; // 유효시간 60분 설정
        String secretKey = "kim-2023-09-01-key";

        String token = JwtTokenUtil.createToken(reqeust.getUserId(),loginMember.getUserRole(), secretKey, expireTimeMs);
        return ApiResponse.onSuccess(token);

    }
}
