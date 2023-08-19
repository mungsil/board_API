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
import study.community.board.domain.dto.v2.CommentDtoV2;
import study.community.board.exception.PostNotFoundException;
import study.community.board.service.CommentService;
import study.community.board.service.MemberService;
import study.community.board.service.PostService;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
1. admin인 경우
2. 성능최적화 - query DSL
3. 게시글 삭제 시 댓글 삭제 문제
( 4. 검색 )
 */
@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/comments")
    public List<CommentDtoV1> findComments(@PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Comment> allComment = commentService.findAllComment(pageable);
        List<CommentDtoV1> commentList = allComment.stream().map(comment -> new CommentDtoV1(comment)).collect(Collectors.toList());
        return commentList;
    }

    // 댓글 달기
    //@Vaild와 @Validation의 차이?
    @PostMapping("/comment")
    public Result<CommentResponse> createComment(@RequestBody @Validated CreateCommentRequest request, Authentication authentication) {
        Post post = postService.findById(request.getPostId());
        Member member = memberService.findMemberByUserId((String) authentication.getPrincipal());
        Comment comment = commentService.saveComment(post, request.getContent(), member);
        CommentResponse commentResponse = new CommentResponse(comment.getId(), comment.getContent(), comment.getMember().getUsername(), comment.getLastModifiedDate());
        return new Result(commentResponse);
    }

    // 댓글 수정
    @PostMapping("/comments/{id}")
    public Result changeComment(@PathVariable(name = "id")Long id, @RequestBody @Validated ChangeCommentRequest request, Authentication authentication) throws AccessDeniedException{
        //댓글id로 찾아온 댓쓴이와 수정자가 같아야함;

        try {
            Comment commentById = commentService.findCommentById(id);
            System.out.println(commentById.toString());
            Member member = memberService.findMemberByUserId((String) authentication.getPrincipal());

            if (!commentById.getMember().equals(member)) {
                throw new AccessDeniedException("해당 댓글을 수정할 권한이 없습니다.");
            }
            Comment comment = commentService.updateComment(id, request.getContent());
            ContentResponse contentResponse = new ContentResponse(comment.getContent());
            //reponse로 변환
            return new Result(contentResponse);
        }catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다.");
        }

    }

/*
    @PostMapping("comments/{id}/{content}")
    public Result changeComment(@PathVariable(name = "id") Long id,@PathVariable(name = "content") String content, Authentication authentication) {
        //댓글id로 찾아온 댓쓴이와 수정자가 같아야함
        Comment commentById = commentService.findCommentById(id);
        System.out.println("====================");
        Member member = memberService.findMemberByUserId((String) authentication.getPrincipal());
        Comment comment = commentService.updateComment(id, content);
        ContentResponse contentResponse = new ContentResponse(comment.getContent());
        return new Result(contentResponse);
    }
*/

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    private static class ChangeCommentRequest {
        String content;
    }


    @Getter
    @AllArgsConstructor
    private static class ContentResponse {
        String content;
    }
    @Data
    @AllArgsConstructor
    public static class Result<T> {
        private T data;
    }

    /*
    non-static inner classes like this can only by instantiated using default, no-argument constructor
     */

    @AllArgsConstructor
    @Getter
    private static class CreateCommentRequest {
        Long postId;
        String content;
    }

/*
    @Getter
    @AllArgsConstructor
    private static class ChangeCommentRequest {
        String content;
    }*/



    // commentId와 같은 정보 반환... 해주는게 맞겠죠?
    @Getter
    @AllArgsConstructor
    private static class CommentResponse {
        Long commentId;
        String content;
        String createdBy;
        LocalDateTime lastModifiedDate;
    }




 // 댓글 삭제
    @DeleteMapping("/comments/{id}")
    public String deleteComment(@PathVariable(name = "id") Long id) {
        commentService.delete(id);
        return "삭제 완";
    }




}
