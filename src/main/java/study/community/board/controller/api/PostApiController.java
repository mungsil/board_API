package study.community.board.controller.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import study.community.board.domain.Post;
import study.community.board.domain.dto.v2.CommentDtoV2;
import study.community.board.domain.dto.PostDto;
import study.community.board.service.CommentService;
import study.community.board.service.PostService;

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

    //전체 글 조회: 페이징 적용
    @GetMapping("/posts")
    public List<PostDto> findPosts(@PageableDefault(size = 10, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> allPost = postService.findAllPost(pageable);
        List<PostDto> postDtoList = allPost.stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());
        return postDtoList;
    }


    //특정


    //특정 게시글 조회 - 해당 게시글에 달린 댓글을 페이징 처리와 함께 반환해야함
    @GetMapping("/posts/{id}")
    public PostDtoWithComments findPostWithComment(@PathVariable(name = "id") Long id) {
        PostDtoWithComments postDto = postService.findById(id).map(post -> new PostDtoWithComments(post)).orElse(null);
        return postDto;
    }

    //== 검색 기능 ==// post 제목 또는 내용으로 조회
    //@GetMapping("/search/posts") @RequestParam사용

    //== 검색 기능 ==// post를 작성한 username으로 post를 조회



    // 글쓰기 *요청 이름 맞춰줘야하나? posts로.
    /*@PostMapping("/posts")
    public CreatePostResponse writePosts(@RequestBody @Validated CreatePostRequest postRequest) {
        Post.createPost(postRequest.getTitle(), postRequest.getContent(), 0, postRequest.getUsername());

    }*/

    // 게시글 수정



    // 게시글 삭제

    @Getter
    @AllArgsConstructor
    private static class CreatePostRequest {
        String username;
        String title;
        String content;
    }


    @Getter
    @AllArgsConstructor
    private static class CreatePostResponse {
        String username;
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

}
