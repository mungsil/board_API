package study.community.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.Comment;
import study.community.board.domain.Member;
import study.community.board.domain.Post;
import study.community.board.exception.CommentNotFoundException;
import study.community.board.repository.CommentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // no flush
public class CommentService {

    private final CommentRepository commentRepository;


    @Transactional
    public Comment saveComment(Post post, String content, Member member) {
        Comment comment = Comment.createComment(post, content, member);
        Comment save = commentRepository.save(comment);
        return save;
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
