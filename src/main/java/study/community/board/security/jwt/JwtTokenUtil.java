package study.community.board.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.util.Date;

public class JwtTokenUtil {

    // 토큰 발급
    public static String createToken(String userId, String secretKey, long expireTimeMS) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMS))
                .signWith(SignatureAlgorithm.ES256, secretKey)
                .compact();
    }

    // Token Parsing Method using Secret Key
    private static Claims extractClaims(String token, String key) {
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
    public static boolean extractIsExpire(String token, String key) {
        Date expiration = extractClaims(token, key).getExpiration();
        return expiration.before(new Date());
    }

}
