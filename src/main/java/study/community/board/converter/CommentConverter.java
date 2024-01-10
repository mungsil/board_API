package study.community.board.converter;

import org.springframework.data.domain.Page;
import study.community.board.domain.Comment;
import study.community.board.dto.CommentRequest;
import study.community.board.dto.CommentResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {
    public static List<CommentResponse.findCommentResultDTO> toFindCommentResultDTO(Page<Comment> commentPage) {

        return commentPage.stream().map(comment -> new CommentResponse.findCommentResultDTO(comment)).collect(Collectors.toList());
    }

    public static CommentResponse.CreateResultDTO toCreateResultDTO(Comment comment) {
        return new CommentResponse.CreateResultDTO(
                comment.getId(), comment.getContent(), comment.getMember().getUsername(), comment.getLastModifiedDate());
    }

    public static CommentRequest.ChangeDTO toChangeDTO(Comment comment) {
        return new CommentRequest.ChangeDTO(comment.getContent());
    }
}
