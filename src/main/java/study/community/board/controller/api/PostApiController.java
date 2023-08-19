package study.community.board.controller.api;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import study.community.board.domain.Member;
import study.community.board.domain.Post;
import study.community.board.domain.dto.v2.CommentDtoV2;
import study.community.board.domain.dto.PostDto;
import study.community.board.exception.PostNotFoundException;
import study.community.board.service.AuthenticationService;
import study.community.board.service.CommentService;
import study.community.board.service.MemberService;
import study.community.board.service.PostService;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * post를 쓰는 건 member 로그인이 구현되어야 해결되는 문제이다. comment도 마찬가지네요
 * 어떤 식으로 프론트엔드와 로그인 정보를 주고 받는지 안다면 먼저 작성해도 되는데...
 */

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;
    private final AuthenticationService authenticationService;

    //전체 글 조회: 페이징 적용
    @GetMapping("/posts/list")
    public Result findPosts(@PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> allPost = postService.findAllPost(pageable);
        List<PostDto> postDtoList = allPost.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());
        return new Result(postDtoList);
    }
    //게시글 조회 - 해당 게시글에 달린 댓글을 페이징 처리와 함께 반환
    @GetMapping("/posts/{id}")
    public Result findPostWithComment(@PathVariable(name = "id") Long id) {
        try {
            Post post = postService.findById(id);
            PostDtoWithComments postDtoWithComments = new PostDtoWithComments(post);
            return new Result(postDtoWithComments);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다.", e);
        }

    }

    //== 검색 기능 ==// post 제목 또는 내용으로 조회
    //@GetMapping("/search/posts") @RequestParam사용

    //== 검색 기능 ==// post를 작성한 username으로 post를 조회

    @PostMapping("/post")
    public Result writePosts(@RequestBody @Validated CreatePostRequest postRequest, Authentication authentication) {

        Member member = memberService.findMemberByUserId((String) authentication.getPrincipal());

        Post post = Post.createPost(postRequest.getTitle(), postRequest.getContent(), 0, member);
        postService.savePost(post);
        PostResponse postResponse = new PostResponse(post.getTitle(), post.getContent(), post.getMember().getUsername());

        return new Result(postResponse);
    }


    // 게시글 수정
    @PostMapping("posts/{id}")
    public Result changePost(@RequestBody @Validated ChangeRequest changeRequest,@PathVariable(name = "id")Long id,Authentication authentication) throws AccessDeniedException {

        // *수정 필* 오류 잡아주기
        try {
            Post post = postService.findById(id);

            if (!post.getMember().getUserId().equals(authentication.getPrincipal())) {
                throw new AccessDeniedException("해당 게시글을 수정할 권한이 없습니다.");}

            Post modifiedPost = postService.modifyPost(post, changeRequest.getTitle(), changeRequest.getContent());

            PostResponse postResponse = new PostResponse(modifiedPost.getTitle(), modifiedPost.getContent(),modifiedPost.getMember().getUsername());
            return new Result(postResponse);

        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다.", e);
        }

    }

    // *수정 필* 게시글 삭제: 게시글을 삭제하면 댓글도 자동 삭제 -> 댓글은 남아있게 하고 시푼데...
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id")Long id) {

        try{
            postService.deletePost(id);
        } catch (PostNotFoundException e){
            return ResponseEntity.notFound().build(); //404 not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
        return ResponseEntity.ok("게시글이 삭제되었습니다");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Result<T> {
        private T data;
    }
    @Getter
    @AllArgsConstructor
    private static class CreatePostRequest {
        String title;
        String content;
    }


    @Getter
    @AllArgsConstructor
    private static class PostResponse {
        String title;
        String content;
        String createdBy;
    }

    @Getter
    private class PostDtoWithComments {
        String title;
        String content;
        String username;
        LocalDateTime lastModifiedDate;
        List<CommentDtoV2> commentDtoList = new ArrayList<>();


        public PostDtoWithComments(Post post) {
            this.title = post.getTitle();
            this.content = post.getContent();
            this.username = post.getMember().getUsername();
            this.lastModifiedDate = post.getLastModifiedDate();
            PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
            this.commentDtoList = commentService.findCommentByPost(post.getId(), pageRequest)
                    .stream()
                    .map(comment -> new CommentDtoV2(comment))
                    .collect(Collectors.toList());
        }

    }
    @Getter
    @AllArgsConstructor
    private static class ChangeRequest {
        String title;
        String content;
    }
}
