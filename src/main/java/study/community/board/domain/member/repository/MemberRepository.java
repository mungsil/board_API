package study.community.board.domain.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.community.board.domain.comment.entity.Comment;
import study.community.board.domain.post.entity.Post;
import study.community.board.domain.member.entity.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUserId(String userId);
    boolean existsByUsername(String username);

    @Query("select m from Member m where m.userId = :userId")
    Optional<Member> findByUserId(@Param("userId") String userId);

    @Query(value = "select p from Post p join fetch p.member where p.member.id = :id",
            countQuery = "select count(p) from Post p")
    Page<Post> findPostsBymemberId(@Param("id") Long memberId, Pageable pageable);

    @Query(value = "select c from Comment c join fetch c.member join fetch c.post where c.member.id = :id",
            countQuery = "select count (c) from Comment c")
    Page<Comment> findCommentsByMemberID(@Param("id") Long memberId, Pageable pageable);

    Page<Member> findAll(Pageable pageable);
   Optional<String> findByUsername(String name);


    Member findMemberByUsername(String username);
}
