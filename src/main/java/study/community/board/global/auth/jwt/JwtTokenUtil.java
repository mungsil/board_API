package study.community.board.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import study.community.board.global.auth.PrincipalDetailsService;
import study.community.board.domain.member.entity.UserRole;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    static PrincipalDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private static String SECRET;
    @Value("${jwt.access-token-expire-time}")
    private static long ACCESS_TOKEN_EXPIRE_TIME;

    // 토큰 발급
    public static String createToken(String userId, UserRole role) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims) // public claim
                .setIssuedAt(new Date(System.currentTimeMillis())) //헤더
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME)) //헤더
                .signWith(SignatureAlgorithm.HS256, SECRET)
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

