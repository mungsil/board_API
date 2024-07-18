package study.community.board.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.comment.entity.Comment;
import study.community.board.domain.member.entity.Member;
import study.community.board.domain.post.entity.Post;
import study.community.board.domain.comment.dto.CommentRequest;
import study.community.board.global.error.exception.CommentNotFoundException;
import study.community.board.domain.comment.repository.CommentRepository;
import study.community.board.domain.member.repository.MemberRepository;
import study.community.board.domain.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // no flush
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public Comment saveComment(CommentRequest.CreateDTO request, String userId) {
        Post post = postRepository.findById(request.getPostId()).get();
        Member member = memberRepository.findByUserId(userId).get();
        Comment comment = Comment.createComment(post, request.getContent(), member);

        return commentRepository.save(comment);
    }

    public Comment findById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentNotFoundException("해당 댓글이 존재하지않습니다."));
        return comment;
    }
    public Page<Comment> findAllComment(Pageable pageable) {
        return commentRepository.findAllComment(pageable);
    }

    //comment가 없는 경우도 고려
    public Page<Comment> findCommentByPostId(Long id, Pageable pageable) {
        return commentRepository.findCommentsByPost(id, pageable);
    }

    @Transactional
    public Comment updateComment(Long commentId, String content) {
        Comment comment = findById(commentId).updateComment(content);
        return comment;
    }

    @Transactional
    public void delete(Long commentId) {
        Comment comment = findById(commentId);
        commentRepository.delete(comment);
    }

}
