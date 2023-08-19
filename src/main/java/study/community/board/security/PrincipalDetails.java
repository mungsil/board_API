package study.community.board.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.community.board.domain.Member;

import java.util.Collection;
import java.util.List;

//post /login 요청을 시큐리티가 가로채서 로그인을 진행해준다. 따라서 직접 해당 요청을 만들 필요가 없다.
//로그인에 성공하면 Security Session을 생성해주는데, 이것은 Security Session(Authentication(UserDetails)) 의 구조로 되어있다.
//PrincipalDetails에서 UserDetails를 정의한다.

public class PrincipalDetails implements UserDetails {

    private Member member;

    //시큐리티 로그인 시 사용
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    // 인가를 위한 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(member.getUserRole().name()));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    public String getUserId() {
        return member.getUserId();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
