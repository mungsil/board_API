package study.community.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    private Post( String title, String content, int recommendedNum) {

        this.title = title;
        this.content = content;
        this.recommendedNum = recommendedNum;
    }

    public static Post createPost(String title, String content, int recommendedNum) {
        return new Post(title, content, recommendedNum);
    }


    //== 연관 관계 편의 메소드 ==//
    public void addMember(Member member) {
        this.member = member;
        member.getPostList().add(this);
    }


}
