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

import java.util.List;
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

    @Transactional
    public Long saveMember(Member member) {
        return memberRepository.save(member).getId();
    }

    public MemberDtoV2 findMember(Long id) {
        return memberRepository.findMemberById(id);
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

    public Page<Post> findPostByMemberId(Long id, Pageable pageable) {
        return memberRepository.findPostsBymemberId(id, pageable);
    }

    public Page<Comment> findCommentsByMemberID(Long id, Pageable pageable) {
        return memberRepository.findCommentsByMemberID(id, pageable);
    }
}
