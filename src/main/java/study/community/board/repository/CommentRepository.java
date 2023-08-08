package study.community.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.community.board.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c join fetch c.member join fetch c.post",
        countQuery = "select count (c) from Comment c")
    Page<Comment> findAllComment(Pageable pageable);

    @Query(value = "select c from Comment c join fetch c.member where c.post.id = :id"
    ,countQuery = "select count (c) from Comment c")
    Page<Comment> findCommentsByPost(@Param("id") Long memberId, Pageable pageable);

}
