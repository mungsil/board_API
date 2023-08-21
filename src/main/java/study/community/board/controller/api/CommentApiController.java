package study.community.board.controller.api;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import study.community.board.domain.Comment;
import study.community.board.domain.Member;
import study.community.board.domain.Post;
import study.community.board.domain.dto.v1.CommentDtoV1;
import study.community.board.exception.CommentNotFoundException;
import study.community.board.service.CommentService;
import study.community.board.service.MemberService;
import study.community.board.service.PostService;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;

    //전체 댓글 조회
    @GetMapping("/comments")
    public Result findComments(
            @PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Comment> allComment = commentService.findAllComment(pageable);
        List<CommentDtoV1> commentList
                = allComment.stream().map(comment -> new CommentDtoV1(comment)).collect(Collectors.toList());
        return new Result(commentList);
    }

    // 댓글 생성
    //@Vaild와 @Validation의 차이?
    @PostMapping("/comment")
    public Result<CreateCommentResponse> createComment(@RequestBody @Validated CreateCommentRequest request
            , Authentication authentication) {
        Post post = postService.findById(request.getPostId());
        Member loggedInMember = memberService.findMemberByUserId((String) authentication.getPrincipal());
        Comment comment = commentService.saveComment(post, request.getContent(), loggedInMember );
        CreateCommentResponse commentResponse
                = new CreateCommentResponse(comment.getId(), comment.getContent(), comment.getMember().getUsername(), comment.getLastModifiedDate());
        return new Result(commentResponse);
    }

    // 댓글 수정
    @PostMapping("/comments/{id}")
    public Result changeComment(@PathVariable(name = "id")Long id
            , @RequestBody @Validated ChangeCommentRequest request, Authentication authentication) throws AccessDeniedException{

        //댓글id로 찾아온 댓쓴이와 수정 하려고 하는 자(loggedInMember)가 같아야함
        try {
            Comment commentById = commentService.findById(id);
            Member loggedInMember = memberService.findMemberByUserId((String) authentication.getPrincipal());

            if (!commentById.getMember().equals(loggedInMember)) {
                throw new AccessDeniedException("해당 댓글을 수정할 권한이 없습니다.");
            }
            Comment comment = commentService.updateComment(id, request.getContent());
            ChangeCommentResponse response = new ChangeCommentResponse(comment.getContent());
            return new Result(response);

        }catch (CommentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다.");
        }

    }

    //댓글 삭제
    @DeleteMapping("/comments/{id}")
    public String deleteComment(@PathVariable(name = "id") Long id) {
        try {
            commentService.delete(id);
            return "댓글이 삭제되었습니다.";
        } catch (CommentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다.");
        }
    }
    @Data
    @AllArgsConstructor
    public static class Result<T> {
        private T data;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ChangeCommentRequest {
        String content;
    }
    @Getter
    @AllArgsConstructor
    private static class ChangeCommentResponse {
        String content;
    }

    @AllArgsConstructor
    @Getter
    private static class CreateCommentRequest {
        Long postId;
        String content;
    }

    @Getter
    @AllArgsConstructor
    private static class CreateCommentResponse {
        Long commentId;
        String content;
        String createdBy;
        LocalDateTime lastModifiedDate;
    }
}
