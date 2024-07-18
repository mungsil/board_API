package study.community.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import study.community.board.domain.member.repository.MemberRepository;
@EnableJpaAuditing
@SpringBootApplication
public class BoardApplication {

	MemberRepository memberRepository;
	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
