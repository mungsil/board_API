package study.community.board.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//post /login 요청을 시큐리티가 가로채서 로그인을 진행해준다. 따라서 직접 해당 요청을 만들 필요가 없다.
//로그인에 성공하면 Security Session을 생성해주는데, 이것은 Security Session(Authentication(UserDetails)) 의 구조로 되어있다.
//PrincipalDetails에서 UserDetails를 정의한다.

@AllArgsConstructor
public class PrincipalDetails implements UserDetails {

    private User user;

    // 인가를 위한 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
