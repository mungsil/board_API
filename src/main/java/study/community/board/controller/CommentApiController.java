package study.community.board.controller;

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
import study.community.board.apiPayload.ApiResponse;
import study.community.board.converter.CommentConverter;
import study.community.board.domain.Comment;
import study.community.board.domain.Member;
import study.community.board.domain.Post;
import study.community.board.dto.CommentRequest;
import study.community.board.dto.CommentResponse;
import study.community.board.dto.MemberResponse;
import study.community.board.exception.CommentNotFoundException;
import study.community.board.service.CommentService;
import study.community.board.service.MemberService;
import study.community.board.service.PostService;
import java.nio.file.AccessDeniedException;
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
    public ApiResponse findComments(
            @PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Comment> commentPage = commentService.findAllComment(pageable);
        return ApiResponse.onSuccess(CommentConverter.toFindCommentResultDTO(commentPage));
    }

    // 댓글 생성
    @PostMapping("/comments")
    public ApiResponse createComment(@RequestBody @Validated CommentRequest.CreateDTO request
            , Authentication authentication) {

        //authentication.getPrincipal()는 왜 userId를 반환했을까?
        Comment comment = commentService.saveComment(request, (String) authentication.getPrincipal());

        return ApiResponse.onSuccess(CommentConverter.toCreateResultDTO(comment));
    }

    // 댓글 수정
    @PatchMapping("/comments/{id}")
    public ApiResponse changeComment(@PathVariable(name = "id")Long id
            , @RequestBody @Validated CommentRequest.ChangeDTO request, Authentication authentication) throws AccessDeniedException{

        //댓글id로 찾아온 댓쓴이와 수정 하려고 하는 자(loggedInMember)가 같아야함
        try {
            //=== 언체크 커스텀 예외 만든 후 commentService로 이동 ===
            Comment commentById = commentService.findById(id);
            Member loggedInMember = memberService.findMemberByUserId((String) authentication.getPrincipal());

            if (!commentById.getMember().equals(loggedInMember)) {
                throw new AccessDeniedException("해당 댓글을 수정할 권한이 없습니다.");
            }
            //
            Comment comment = commentService.updateComment(id, request.getContent());
            return ApiResponse.onSuccess(CommentConverter.toChangeDTO(comment));

        }catch (CommentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다.");
        }

    }

    //댓글 삭제
    @DeleteMapping("/comments/{id}")
    public ApiResponse deleteComment(@PathVariable(name = "id") Long id) {
        try {
            commentService.delete(id);
            return ApiResponse.onSuccess("댓글이 삭제되었습니다.");
        } catch (CommentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다.");
        }
    }

}
