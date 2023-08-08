package study.community.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username"})
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    @Column(unique = true,nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    private Member(String username, String userId, String userPassword, Grade grade) {
        this.username = username;
        this.userId = userId;
        this.userPassword = userPassword;
        this.grade = grade;
    }

    public static Member createMember(String username, String userId, String userPassword, Grade grade) {
        return new Member(username, userId, userPassword, grade);
    }
}
