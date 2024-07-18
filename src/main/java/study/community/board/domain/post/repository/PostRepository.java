package study.community.board.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.community.board.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p join fetch p.member", countQuery = "select count(p) from Post p")
    Page<Post> findAllPost(Pageable pageable);

}
