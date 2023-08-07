package study.community.board;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.Comment;
import study.community.board.domain.Grade;
import study.community.board.domain.Member;
import study.community.board.domain.Post;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

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

            Member memberA = Member.createMember("memberA", "kflsd@123", "2222", Grade.C);
            Member memberB = Member.createMember("memberB", "AJLDmm@pspdfj", "1234", Grade.B);
            em.persist(memberA);
            em.persist(memberB);


            String titleA = "모바일 프로그래밍 초과 풀렸음?";
            String titleB = "수강신청 언제임";
            String content = "ㅈㄱㄴ";

            Post postA = Post.createPost(titleA,content,1);
            Post postB = Post.createPost(titleB,content,1);
            postA.addMember(memberA);
            postB.addMember(memberB);
            em.persist(postA);
            em.persist(postB);

            Comment comment1 = Comment.createComment("ㅈㄱㄴ가 뭐임?");
            comment1.addMember(memberB);
            comment1.addPost(postA);
            em.persist(comment1);

            Comment comment2 = Comment.createComment("핑프냐? 검색하셈;");
            comment2.addMember(memberA);
            comment2.addPost(postB);
            em.persist(comment2);



        }



    }
}
