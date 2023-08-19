package study.community.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.controller.dto.CreateMemberRequest;
import study.community.board.controller.dto.LoginMemberRequest;
import study.community.board.domain.Member;
import study.community.board.repository.MemberRepository;
import study.community.board.security.jwt.JwtTokenUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // no flush
public class AuthenticationService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    //userId 중복 체크
    public boolean duplicateIdCheck(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    //userName 중복 체크
    public boolean duplicateNameCheck(String username) {
        return memberRepository.existsByUsername(username);
    }

    //회원가입 *중복체크-> 컨트롤러
    @Transactional
    public String createMember(CreateMemberRequest request) {
        //비밀번호를 암호화하여 저장
        Member member = request.toEntity(passwordEncoder.encode(request.getPassword()));
        return memberRepository.save(member).getUserId();
    }

    //로그인
    public Member Login(LoginMemberRequest request) {
        //id 비교
        String requestUserId = request.getUserId();
        System.out.println("요청 id = "+ request.getUserId());
        Member findmember = memberRepository.findByUserId(requestUserId).orElse(null);

        if (findmember == null) {
            System.out.println("error! id자체가 없음");
            return null;
        }

        //pw 비교
        String requestPw = request.getPassword();
        String userPassword = findmember.getPassword();
        System.out.println(
                requestPw+"  -  "+userPassword
        );

        if(!userPassword.equals(requestPw)){
            System.out.println("error! 비밀번호 틀림");
            return null;
        }

        return findmember;
    }

    public Optional<Member> getMemberFromToken(String token) {
        Optional<Member> member = memberRepository.findByUserId(getUserIdFromToken(token));
        return member;
    }

    private String getUserIdFromToken(String token) {
        String splitedToken = token.split(" ")[1];
        String secretKey = "kim-2023-09-01-key";
        return JwtTokenUtil.extractUserId(splitedToken, secretKey);
    }



}
