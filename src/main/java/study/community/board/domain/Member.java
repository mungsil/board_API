package study.community.board.domain;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
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
    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    private Member(String username, String userId, String password, UserRole userRole) {
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.userRole = userRole;
    }

    public Member updateMember(String username, String password) {
        if (!this.username.equals(username)) {
            this.username = username;
        }
        if (!this.password.equals(password)) {
            this.password = password;
        }
        return this;
    }
    public static Member createMember(String username, String userId, String password, UserRole userRole) {
        return new Member(username, userId, password, userRole);
    }

}
