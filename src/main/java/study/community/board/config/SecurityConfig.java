package study.community.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/*
@Configuration
@EnableWebSecurity // 필터 체인을 사용하기 위함
public class SecurityConfig {


    private final PasswordEncoder pwEncoder=
    PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails memberA = User.builder()
                .username("memberA")
                .password(pwEncoder.encode("1111"))
                .roles("USER")
                .build();

        UserDetails memberB = User.builder()
                .username("memberB")
                .password(pwEncoder.encode("2222"))
                .roles("USER")
                .build();

        // 비밀번호 로깅은 프로덕션 애플리케이션에서는 절대로 사용하지 말 것
        System.out.println(" >>>memberA의 비밀번호 : " + memberA.getPassword());
        System.out.println(" >>>memberB의 비밀번호 : " + memberB.getPassword());

        // UserDetailsManager와 UserDetailsPasswordService 인터페이스를 구현
        return new InMemoryUserDetailsManager(memberA, memberB);

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                //접근 권한을 설정하기 위한 메서드 체인을 시작
                .authorizeRequests()
                //admin만 인가
                .antMatchers("/settings/**").hasAnyRole("ADMIN")
                //members, posts는 인증(로그인)을 해야 접속이 가능
                .antMatchers("/members/**","/posts/**").authenticated()
                //나머지 요청은 인증(로그인)을 안해도 접근 가능
                .anyRequest().permitAll() //anyMatchers().permitAll()과의 차이는
                .and()
                //스프링 시큐리티에서 지원하는 로그인 형태를 사용 가능
                .formLogin()

                //로그인이 필요한 페이지를 들어갔을 때 인가되지 않은 사용자에게 보여줄 페이지 설정한다. 로그인 페이지의 경로를 설정
                .loginPage("/login")
                //로그인 폼에서 제출된 정보를 처리할 URL을 "/loginProc"으로 설정, 여기서 로그인 프로세스 처리 후 토큰 발급
                .loginProcessingUrl("loginProc")
                //로그인 후 돌아갈 페이지를 설정, 홈 말고 이전에 요청한 페이지로 돌아가도록 설정하기 위하여 false 설정
                .defaultSuccessUrl("/",false)
                .successHandler(savedRequestAwareAuthenticationSuccessHandler()) // 성공 핸들러 설정
                .and().build();
    }

    @Bean
    public AuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setUseReferer(true); // Referer를 사용하여 돌아갈 페이지 설정
        return successHandler;
    }
}
*/
