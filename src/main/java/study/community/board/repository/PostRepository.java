package study.community.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.community.board.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
