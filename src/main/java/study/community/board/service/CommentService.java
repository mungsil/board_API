package study.community.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.board.domain.Comment;
import study.community.board.repository.CommentRepository;

import java.util.List;

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

}
