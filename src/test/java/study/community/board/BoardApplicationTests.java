package study.community.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.Member;
import study.community.board.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Rollback(value = false)
@SpringBootTest
@Transactional
class BoardApplicationTests {

	@Autowired
	MemberRepository memberRepository;

	@Test
	public void 멤버조회() throws Exception{
	    //given
	    //initDB

		//when
		Member member = memberRepository.findById(1L).orElse(null);

		//then
		System.out.println("member = " + member);

	}
}
