package study.community.board.domain.post.controller;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.community.board.domain.post.converter.PostConverter;
import study.community.board.domain.post.dto.PostRequest;
import study.community.board.domain.post.entity.Post;
import study.community.board.domain.post.service.PostService;
import study.community.board.global.apiPayload.ApiResponse;
import study.community.board.global.apiPayload.code.ErrorStatus;
import study.community.board.domain.comment.entity.Comment;
import study.community.board.global.error.exception.PostNotFoundException;
import study.community.board.domain.comment.service.CommentService;
import study.community.board.domain.member.service.MemberService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;


    //전체 글 조회: 페이징 적용
    @GetMapping("/posts")
    public ApiResponse findPosts(@PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> postPage = postService.findAllPost(pageable);

        return ApiResponse.onSuccess(PostConverter.toFindPostResultDTOList(postPage));
    }
    //게시글 조회 - 해당 게시글에 달린 댓글을 페이징 처리와 함께 반환
    @GetMapping("/posts/{id}")
    public ApiResponse findPostWithComment(@PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable(name = "id") Long id) {

        Post post = postService.findById(id);
        Page<Comment> commentPage = commentService.findCommentByPostId(post.getId(), pageable);
        List<Comment> commentList = PostConverter.toCommentList(commentPage);
        return ApiResponse.onSuccess(PostConverter.toFindPostWithCommentResultDTO(post, commentList));
    }

    //게시글 작성
    @PostMapping("/posts")
    public ApiResponse CreatePost(@RequestBody @Validated PostRequest.CreatePostDTO request, Authentication authentication) {

        Post post = postService.savePost(request, (String) authentication.getPrincipal());

        return ApiResponse.onSuccess(PostConverter.toCreatePostResultDTO(post));
    }


    // 게시글 수정
    @PatchMapping("posts/{id}")
    public ApiResponse changePost(@RequestBody @Validated PostRequest.ChangePostDTO changeRequest,@PathVariable(name = "id")Long id,Authentication authentication) throws AccessDeniedException {

        Post post = postService.findById(id);

        if (!post.getMember().getUserId().equals(authentication.getPrincipal())) {
            throw new AccessDeniedException("해당 게시글을 수정할 권한이 없습니다.");}
        Post modifiedPost = postService.modifyPost(post, post.getContent(), post.getMember().getUsername());
        return ApiResponse.onSuccess(PostConverter.toChangePostResultDTO(modifiedPost));

    }

    // *수정 필* 게시글 삭제: 게시글을 삭제하면 댓글도 자동 삭제 -> 댓글은 남아있도록 수정
    @DeleteMapping("/posts/{id}")
    public ApiResponse deletePost(@PathVariable(name = "id")Long id) {

        try{
            postService.deletePost(id);
        } catch (PostNotFoundException e){
            return ApiResponse.onFailure(ErrorStatus.POST_BAD_REQUEST.getCode(),"그런 게시글 없어요","실패"); //404 not found
        }
        return ApiResponse.onSuccess("게시글이 삭제되었습니다.");
    }

}
