package study.community.board.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import study.community.board.domain.Member;
import study.community.board.domain.UserRole;
import study.community.board.security.PrincipalDetailsService;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    static PrincipalDetailsService userDetailsService;
    // 토큰 발급
    public static String createToken(String userId, UserRole role, String secretKey, long expireTimeMS) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims) // public claim
                .setIssuedAt(new Date(System.currentTimeMillis())) //헤더
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMS)) //헤더
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Token Parsing Method using Secret Key
    protected static Claims extractClaims(String token, String key) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    // 토큰에서 아이디 추출
    public static String extractUserId(String token, String key) {
        return extractClaims(token, key).get("userId").toString();
    }

    // 토큰에서 만료 시간 추출
    public static Date extractExpireTime(String token, String key){
        return extractClaims(token, key).getExpiration();
    }

    // 유효 시간 체크
    public static boolean isExpired(String token, String key) {
        Date expiration = extractClaims(token, key).getExpiration();
        return expiration.before(new Date());
    }

    public static Authentication getAuthentication(String token, String key) {

        if (token == null) {
            return null;
        }
        System.out.println(key);
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractUserId(token, key));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}

