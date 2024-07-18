package study.community.board.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import study.community.board.global.common.BaseTimeEntity;
import study.community.board.domain.member.entity.Member;
import study.community.board.domain.post.entity.Post;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = "content")
@Builder
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Comment(Post post, String content, Member member) {
        this.content = content;
        addPost(post);
        addMember(member);
    }

    public static Comment createComment(Post post, String content, Member member) {
        return new Comment(post, content, member);
    }

    public Comment updateComment(String content) {
        if (!this.content.equals(content)) {
            this.content = content;
        }
        return this;
    }

    //== 연관관계 메서드 ==//
    public void addPost(Post post) {
        this.post = post;
        post.getCommentList().add(this);
    }

    //== 연관관계 메서드 ==//
    public void addMember(Member member) {
        this.member = member;
        member.getCommentList().add(this);
    }
}
