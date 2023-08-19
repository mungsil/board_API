package study.community.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.Comment;
import study.community.board.domain.Member;
import study.community.board.domain.Post;
import study.community.board.exception.CommnetNotFoundException;
import study.community.board.repository.CommentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // no flush
public class CommentService {

    private final CommentRepository commentRepository;

    public Page<Comment> findAllComment(Pageable pageable) {
        return commentRepository.findAllComment(pageable);
    }

    public Page<Comment> findCommentByPost(Long id, Pageable pageable) {
        return commentRepository.findCommentsByPost(id, pageable);
    }

    @Transactional
    public Comment saveComment(Post post, String content, Member member) {
        Comment comment = Comment.createComment(post, content, member);
        Comment save = commentRepository.save(comment);
        return save;
    }

    @Transactional
    public Comment updateComment(Long commentId, String content) {
        Comment comment = findCommentById(commentId).updateComment(content);
        return comment;
    }

    public Comment findCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new CommnetNotFoundException("해당 댓글이 존재하지않습니다."));
        return comment;
    }

    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        System.out.println(comment);
        commentRepository.delete(comment);

    }

}
