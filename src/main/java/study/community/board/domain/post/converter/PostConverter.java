package study.community.board.domain.post.converter;

import org.springframework.data.domain.Page;
import study.community.board.domain.comment.entity.Comment;
import study.community.board.domain.post.dto.PostResponse;
import study.community.board.domain.post.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {
    public static List<PostResponse.findPostResultDTO> toFindPostResultDTOList(Page<Post> postPage) {
        return postPage.stream()
                .map(post -> new PostResponse.findPostResultDTO(post))
                .collect(Collectors.toList());
    }

    public static List<Comment> toCommentList(Page<Comment> commentPage) {
        return commentPage.stream()
                .map(comment -> Comment.createComment(comment.getPost(),comment.getContent(),comment.getMember()))
                .collect(Collectors.toList());
    }

    public static PostResponse.FindPostWithCommentResultDTO toFindPostWithCommentResultDTO(Post post,List<Comment> commentList) {
        return new PostResponse.FindPostWithCommentResultDTO(post,commentList);
    }

    public static PostResponse.CreatePostResultDTO toCreatePostResultDTO(Post post) {
        return new PostResponse.CreatePostResultDTO(post.getTitle(), post.getContent(), post.getMember().getUsername());
    }

    public static PostResponse.ChangePostResultDTO toChangePostResultDTO(Post post) {
        return new PostResponse.ChangePostResultDTO(
                post.getTitle(), post.getContent(),post.getMember().getUsername());
    }
}

