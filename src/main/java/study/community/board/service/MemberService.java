package study.community.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.controller.dto.MemberDto;
import study.community.board.domain.Member;
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

    public Member findMember(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public String findUsername(String name) {
        Optional<String> findName = memberRepository.findByUsername(name);
        return findName.orElseGet(this::defaultUsername);
    }

    public MemberDto findMemberDto(String name) {
        MemberDto memberByUsername = memberRepository.findMemberByUsername(name);
        return memberByUsername;
    }

    private String defaultUsername() {
        return "anonymous";
    }

    /*public List<Member> findAllMember() {
        return memberRepository.findAll();
    }*/

}
