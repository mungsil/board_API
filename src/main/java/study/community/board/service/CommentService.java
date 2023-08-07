package study.community.board.service;

import lombok.RequiredArgsConstructor;
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

    public List<Comment> findAllComment() {
        return commentRepository.findAll();
    }
}
