package study.community.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    //Long id로 member 조회  *인증, 인가 시 사용한다.
    public MemberDtoV2 findMemberById(Long id) {
        //로그인이 안되어있는 경우
        if (id == null) {return null;}

        MemberDtoV2 findMember = memberRepository.findMemberById(id);
        if (findMember==null) {
            return null;
        }
        return findMember;
    }

    //String userId로 member 조회  *인증, 인가 시 사용한다.
    public MemberDtoV2 findMemberDtoByUserId(String userId) {
        //로그인이 안되어있는 경우
        if (userId == null) {return null;}

        Member findMember = memberRepository.findByUserId(userId).orElse(null);
        if (findMember==null) {
            return null;
        }
        return new MemberDtoV2(findMember.getUsername(), findMember.getUserId(), findMember.getUserRole());
    }

    public Member findMemberByUserId(String userId) {
        if (userId == null) {
            return null;
        }
        return memberRepository.findByUserId(userId).orElse(null);
    }


    // 닉네임으로 member 조회
    public String findUsername(String name) {
        Optional<String> findName = memberRepository.findByUsername(name);
        return findName.orElseGet(this::defaultUsername);
    }

    public Member findMemberByName(String name) {
        return memberRepository.findMemberByUsername(name);
    }

    /*public Page<Member> findAllById(Long id, Pageable pageable) {
        Page<Member> allById = memberRepository.findAllById(id, pageable);
        return allById;
    }*/

    private String defaultUsername() {
        return "anonymous";
    }

    // 전체 멤버 조회
    public Page<Member> findAllMember(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    // memberId로 Member의 post를 찾아서 반환
    public Page<Post> findPostByMemberId(Long id, Pageable pageable) {
        Page<Post> postsBymemberId = memberRepository.findPostsBymemberId(id, pageable);
        if (postsBymemberId.isEmpty()) {
            return null;
        }
        return postsBymemberId;
    }

    // memberId로 Member의 Comment를 찾아서 반환
    public Page<Comment> findCommentsByMemberID(Long id, Pageable pageable) {
        return memberRepository.findCommentsByMemberID(id, pageable);
    }
}
