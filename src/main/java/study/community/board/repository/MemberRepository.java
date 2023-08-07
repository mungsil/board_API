package study.community.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.community.board.controller.dto.MemberDto;
import study.community.board.domain.Member;

import java.util.Optional;

//이름이 똑같으면 어케 되는거지? 걍 파리미터도 똑같으면? 반환타입만 달라도 구분 되겠죠?
public interface MemberRepository extends JpaRepository<Member, Long> {

 //   MemberDto findMemberByGrade();

    MemberDto findMemberByUsername(String name);

    Optional<String> findByUsername(String name);
}
