package study.community.board.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.member.entity.Member;
import study.community.board.domain.post.dto.PostRequest;
import study.community.board.domain.post.entity.Post;
import study.community.board.domain.post.repository.PostRepository;
import study.community.board.global.error.exception.PostNotFoundException;
import study.community.board.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // no flush
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public Post savePost(PostRequest.CreatePostDTO request, String userId) {
        Member member = memberRepository.findByUserId(userId).get();
        Post post = Post.createPost(request.getTitle(), request.getContent(), 0, member);

        return postRepository.save(post);
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
}
