package study.community.board.controller;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.community.board.apiPayload.ApiResponse;
import study.community.board.apiPayload.code.ErrorStatus;
import study.community.board.domain.Comment;
import study.community.board.domain.Member;
import study.community.board.domain.Post;
import study.community.board.dto.MemberResponse;
import study.community.board.dto.PostRequest;
import study.community.board.dto.PostResponse;
import study.community.board.exception.PostNotFoundException;
import study.community.board.service.CommentService;
import study.community.board.service.MemberService;
import study.community.board.service.PostService;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;


    //전체 글 조회: 페이징 적용
    @GetMapping("/posts")
    public ApiResponse findPosts(@PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> allPost = postService.findAllPost(pageable);
        //--이미 MemberResponse에 있어도 postResponseDTO로 만들어줘야할까? --//
        List<MemberResponse.findPostResultDTO> findPostResultDTOList = allPost.stream()
                .map(post -> new MemberResponse.findPostResultDTO(post))
                .collect(Collectors.toList());
        return ApiResponse.onSuccess(findPostResultDTOList);
    }
    //게시글 조회 - 해당 게시글에 달린 댓글을 페이징 처리와 함께 반환
    @GetMapping("/posts/{id}")
    public ApiResponse findPostWithComment(@PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable(name = "id") Long id) {

        Post post = postService.findById(id);
        Page<Comment> commentByPostId = commentService.findCommentByPostId(post.getId(), pageable);

        PostResponse.findPostResultDTO findPostDto = new PostResponse.findPostResultDTO(post,commentByPostId);
        return ApiResponse.onSuccess(findPostDto);
    }

    //게시글 작성
    @PostMapping("/posts")
    public ApiResponse CreatePost(@RequestBody @Validated PostRequest.CreatePostDTO request, Authentication authentication) {

        Member member = memberService.findMemberByUserId((String) authentication.getPrincipal());

        Post post = Post.createPost(request.getTitle(), request.getContent(), 0, member);
        postService.savePost(post);
        PostResponse.CreatePostResultDTO postResultDTO =
                new PostResponse.CreatePostResultDTO(post.getTitle(), post.getContent(), post.getMember().getUsername());
        return ApiResponse.onSuccess(postResultDTO);
    }


    // 게시글 수정
    @PatchMapping("posts/{id}")
    public ApiResponse changePost(@RequestBody @Validated PostRequest.ChangePostDTO changeRequest,@PathVariable(name = "id")Long id,Authentication authentication) throws AccessDeniedException {

        Post post = postService.findById(id);

        if (!post.getMember().getUserId().equals(authentication.getPrincipal())) {
            throw new AccessDeniedException("해당 게시글을 수정할 권한이 없습니다.");}
        Post modifiedPost = postService.modifyPost(post, post.getContent(), post.getMember().getUsername());
        PostResponse.ChangePostResultDTO postResponse =
                 new PostResponse.ChangePostResultDTO(modifiedPost.getTitle(), modifiedPost.getContent(),modifiedPost.getMember().getUsername());
         return ApiResponse.onSuccess(postResponse);

    }

    // *수정 필* 게시글 삭제: 게시글을 삭제하면 댓글도 자동 삭제 -> 댓글은 남아있도록 수정
    @DeleteMapping("/posts/{id}")
    public ApiResponse deletePost(@PathVariable(name = "id")Long id) {

        try{
            postService.deletePost(id);
        } catch (PostNotFoundException e){
            return ApiResponse.onFailure(ErrorStatus.POST_BAD_REQUEST.getCode(),"그런 게시글 없어요","실패"); //404 not found
        }
        return ApiResponse.onSuccess("삭제 완료");
    }

}
