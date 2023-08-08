package study.community.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.Member;
import study.community.board.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    public void 멤버조회() throws Exception {
        //given
        //initDB

        /*//when
        Member member = memberService.findMember(1L);

        //then
        System.out.println("member = " + member);*/

    }
}