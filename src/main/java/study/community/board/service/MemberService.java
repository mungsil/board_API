package study.community.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.controller.dto.CreateMemberRequest;
import study.community.board.controller.dto.LoginMemberRequest;
import study.community.board.domain.Comment;
import study.community.board.domain.Post;
import study.community.board.domain.Member;
import study.community.board.domain.dto.v2.MemberDtoV2;
import study.community.board.repository.MemberRepository;

import java.util.Optional;

/**
 * 1.로그인(join)기능은 일단 saveMember로 간단하게 대체
 * 2. optional 대해서 더 알아보기
 * 3. findName.orElseGet(this::defaultUsername);
 * 4. 페이징을 위한 메서드가 memberService에 필요할까? 어차피 다 쿼리로 하는거 아닌가?
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // no flush
public class MemberService {

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

    //Long id로 member 조회  *인증, 인가 시 사용한다.
    public MemberDtoV2 findMemberById(Long id) {
        //로그인이 안되어있는 경우
        if (id == null) {return null;}

        MemberDtoV2 memberById = memberRepository.findMemberById(id);
        if (memberById==null) {
            return null;
        }
        return memberById;
    }

    //String userId로 member 조회  *인증, 인가 시 사용한다.
    public MemberDtoV2 findMemberByUserId(String userId) {
        //로그인이 안되어있는 경우
        if (userId == null) {return null;}

        Member byUserId = memberRepository.findByUserId(userId).orElse(null);
        if (byUserId==null) {
            return null;
        }
        return new MemberDtoV2(byUserId.getUsername(), byUserId.getUserId(), byUserId.getUserRole());
    }


    
    public String findUsername(String name) {
        Optional<String> findName = memberRepository.findByUsername(name);
        return findName.orElseGet(this::defaultUsername);
    }

    /*public Page<Member> findAllById(Long id, Pageable pageable) {
        Page<Member> allById = memberRepository.findAllById(id, pageable);
        return allById;
    }*/

    private String defaultUsername() {
        return "anonymous";
    }

    public Page<Member> findAllMember(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    //memberId로 post를 찾아서 반환
    public Page<Post> findPostByMemberId(Long id, Pageable pageable) {
        Page<Post> postsBymemberId = memberRepository.findPostsBymemberId(id, pageable);
        if (postsBymemberId.isEmpty()) {
            return null;
        }
        return postsBymemberId;
    }

    //memberId로 Comment를 찾아서 반환
    public Page<Comment> findCommentsByMemberID(Long id, Pageable pageable) {
        return memberRepository.findCommentsByMemberID(id, pageable);
    }
}
