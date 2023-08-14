package study.community.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username"})
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    //unique제약조건 어케 걸었더라
    private String username;

    @Column(unique = true,nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    private Member(String username, String userId, String userPassword, UserRole userRole) {
        this.username = username;
        this.userId = userId;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    public static Member createMember(String username, String userId, String userPassword, UserRole userRole) {
        return new Member(username, userId, userPassword, userRole);
    }
}
