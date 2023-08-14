package study.community.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import study.community.board.repository.MemberRepository;


/**
 * @GeneratedId에서 독립적인 id값 생성되도록. 아 그리고 mySQL은 1,2,3... 이런거 아니지 않았나?
 * > @TableGenerator로 identity전략이 아닌 테이블 전략을 사용하면 해결가능하다. 근ㄷㅔ 굳이 싶어서 나중에 하기루...
 */
@EnableJpaAuditing
@SpringBootApplication
public class BoardApplication {

	MemberRepository memberRepository;
	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
