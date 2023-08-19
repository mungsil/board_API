package study.community.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.Member;
import study.community.board.domain.Post;
import study.community.board.exception.PostNotFoundException;
import study.community.board.repository.PostRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // no flush
public class PostService {

    private final PostRepository postRepository;
    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Page<Post> findAllPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post findById(Long id) {
        Post byId = postRepository.findById(id)
                .orElseThrow(()-> new PostNotFoundException("해당 post가 존재하지 않습니다."));

        return byId;
    }

    @Transactional
    public Post modifyPost(Post post, String title, String content) {
        return post.updatePost(title, content);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("해당하는 post가 존재하지 않습니다."));
        postRepository.delete(post);
    }

    public boolean isEqual(Member member, Post post) {
        return post.setChangeEnable(member.equals(post.getMember()));
    }

}
