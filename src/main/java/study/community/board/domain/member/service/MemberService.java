package study.community.board.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import study.community.board.domain.comment.entity.Comment;
import study.community.board.domain.member.dto.MemberResponse;
import study.community.board.domain.member.entity.Member;
import study.community.board.domain.member.repository.MemberRepository;
import study.community.board.domain.post.entity.Post;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // no flush
public class MemberService {

    private final MemberRepository memberRepository;

    //Long id로 member 조회  *인증, 인가 시 사용한다.
    public Member findMemberById(Long id) {
        if (id == null) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"아이디를 입력해주세요");}

        return memberRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 계정이 존재하지 않습니다."));

    }

    //String userId로 member 조회  *인증, 인가 시 사용한다.
    public MemberResponse.findMemberResultDTO findMemberDtoByUserId(String userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}
        Member findMember = memberRepository
                .findByUserId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new MemberResponse.findMemberResultDTO(findMember.getUsername(), findMember.getUserId(), findMember.getUserRole());
    }

    public Member findMemberByUserId(String userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}
        return memberRepository.findByUserId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Member findMemberByName(String name) {
        return memberRepository.findMemberByUsername(name);
    }

    // 전체 멤버 조회
    public Page<Member> findAllMember(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    // memberId로 Member의 post를 찾아서 반환
    public Page<Post> findPostByMemberId(Long id, Pageable pageable) {
        Page<Post> postsBymemberId = memberRepository.findPostsBymemberId(id, pageable);
        if (postsBymemberId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"작성된 글이 없습니다.");
        }
        return postsBymemberId;
    }

    // memberId로 Member의 Comment를 찾아서 반환
    public Page<Comment> findCommentsByMemberID(Long id, Pageable pageable) {
        return memberRepository.findCommentsByMemberID(id, pageable);
    }

    @Transactional
    public Member updateInfo(String username,String password,Long id) {
       return findMemberById(id).updateMember(username, password);
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.delete(findMemberById(id));
    }
}
