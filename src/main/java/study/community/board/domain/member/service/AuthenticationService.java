package study.community.board.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.member.entity.Member;
import study.community.board.domain.member.repository.MemberRepository;
import study.community.board.domain.member.dto.JwtRequest;

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

    //회원가입 *중복체크는-> 컨트롤러
    @Transactional
    public String createMember(JwtRequest.CreateMemberDTO request) {
        //비밀번호를 암호화하여 저장
        Member member = request.toEntity(passwordEncoder.encode(request.getPassword()));
        return memberRepository.save(member).getUserId();
    }

    //로그인
    public Member Login(JwtRequest.LoginMemberDTO request) {
        //id 비교
        String requestUserId = request.getUserId();
        System.out.println("요청 id = "+ request.getUserId());
        Member findmember = memberRepository.findByUserId(requestUserId).orElse(null);

        if (findmember == null) {
            return null;
        }

        //pw 비교
        String requestPw = request.getPassword();
        String userPassword = findmember.getPassword();

        if(!userPassword.equals(requestPw)){
            return null;
        }

        return findmember;
    }

}
