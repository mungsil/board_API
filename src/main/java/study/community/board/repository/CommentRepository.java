package study.community.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.community.board.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
