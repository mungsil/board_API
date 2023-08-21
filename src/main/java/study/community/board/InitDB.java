package study.community.board;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.Comment;
import study.community.board.domain.UserRole;
import study.community.board.domain.Member;
import study.community.board.domain.Post;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em; //엔티티를 조회하고 변경하기 위해서 필요함

        public void dbInit() {

            Member memberA = Member.createMember("memberA", "kflsd@123", "2222", UserRole.USER);
            Member memberB = Member.createMember("memberB", "AJLDmm@pspdfj", "1234", UserRole.ADMIN);
            Member member3 = Member.createMember("member3", "siuohfua@naver.com", "1111", UserRole.USER);
            em.persist(memberA);
            em.persist(memberB);
            em.persist(member3);


            String titleA = "모바일 프로그래밍 초과 풀렸음?";
            String titleB = "수강신청 언제임";
            String content = "ㅈㄱㄴ";

            Post postA = Post.createPost(titleA,content,0, memberA);
            Post postB = Post.createPost(titleB,content,0, memberB);
            em.persist(postA);
            em.persist(postB);

            Comment comment1 = Comment.createComment(postA,"ㅈㄱㄴ가 뭐임?",memberB);
            em.persist(comment1);

            Comment comment2 = Comment.createComment(postB,"핑프냐? 검색하셈;",memberA);
            em.persist(comment2);

            Comment comment3 = Comment.createComment(postB, "오늘 12시", member3);
            em.persist(comment3);

        }
    }
}
