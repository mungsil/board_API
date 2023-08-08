package study.community.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.Comment;
import study.community.board.domain.Post;
import study.community.board.repository.PostRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // no flush
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> findAllPost(Pageable pageable) {
        return postRepository.findAllPost(pageable);
    }


    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

}
