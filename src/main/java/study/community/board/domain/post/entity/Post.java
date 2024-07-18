package study.community.board.domain.post.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.community.board.global.common.BaseTimeEntity;
import study.community.board.domain.comment.entity.Comment;
import study.community.board.domain.member.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@Setter
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;
    private int recommendedNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    private boolean changeEnable;

    private Post(String title, String content, int recommendedNum, Member member) {

        this.title = title;
        this.content = content;
        this.recommendedNum = recommendedNum;
        addMember(member);
    }

    public static Post createPost(String title, String content, int recommendedNum, Member member) {
        return new Post(title, content, recommendedNum, member);

    }

    public Post updatePost(String title, String content) {
        if (!title.isEmpty()) {
            this.title = title;
        }
        if(!content.isEmpty()){
            this.content = content;
        }
        return this;
    }

    //== 연관 관계 편의 메소드 ==//
    public void addMember(Member member) {
        this.member = member;
        member.getPostList().add(this);
    }

}
