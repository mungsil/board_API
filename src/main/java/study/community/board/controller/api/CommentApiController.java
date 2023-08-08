package study.community.board.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.community.board.domain.Comment;
import study.community.board.domain.dto.v1.CommentDtoV1;
import study.community.board.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    // 전체 댓글 조회- admin
    @GetMapping("/comments")
    public List<CommentDtoV1> findComments(@PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Comment> allComment = commentService.findAllComment(pageable);
        List<CommentDtoV1> commentList = allComment.stream().map(comment -> new CommentDtoV1(comment)).collect(Collectors.toList());
        return commentList;
    }



    // 댓글 달기

    // 댓글 삭제

    // 댓글 수정











}
