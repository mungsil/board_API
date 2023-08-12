package study.community.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.controller.dto.CreateMemberRequest;
import study.community.board.controller.dto.LoginMemberRequest;
import study.community.board.domain.Member;
import study.community.board.repository.MemberRepository;

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
        Member member = request.toEntity(passwordEncoder.encode(request.getUserPassword()));
        return memberRepository.save(member).getUserId();
    }

    //로그인
    public String Login(LoginMemberRequest request) {
        //id 비교
        String requestUserId = request.getUserId();
        Member findmember = memberRepository.findByUserId(requestUserId).orElse(null);

        if (findmember == null) {
            return null;
        }

        //pw 비교
        String userPassword = findmember.getUserPassword();
        String requestPw = request.getUserPassword();
        if(!userPassword.equals(requestPw)){
            return null;
        }

        return findmember.getUsername();
    }


}
