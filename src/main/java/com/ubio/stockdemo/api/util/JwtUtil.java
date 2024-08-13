package com.ubio.stockdemo.api.util;

import com.ubio.stockdemo.model.dto.JwtDto;
import com.ubio.stockdemo.model.dto.StockToken;
import com.ubio.stockdemo.model.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
@Component
public class JwtUtil {
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public static String getSubject(String jwt) {
        Jws<Claims> claimsJws;
        return null;
    }

    public String createToken(User user, StockToken stockToken) {
//  user 정보를 이용하여 JWT 토큰을 생성하는 메소드
        Date nowDate = new Date();
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

//        access token 생성
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
//                .claim(AUTHORITIES_KEY, user.getPrivilege().getId())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setIssuedAt(nowDate)
                .setExpiration(validity)
                .issuer("uc_stock")
                .claim("name", user.getUsername())
//                .claim("accountNo", user.getRealCano())
//                .claim("appKey", user.getAppKey())
//                .claim("appSecret", user.getAppSecret())
                .claim("access_token", stockToken.getAccess_token())
                .claim("token_token_expired", stockToken.getAccess_token_token_expired())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            log.info("Token expired");
            return null;
//
        }catch (JwtException e){
            return null;
        }
    }

    public String extractUserLoginId(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractPrivilegeId(String token) {
        return extractAllClaims(token).get(AUTHORITIES_KEY, String.class);
    }

    public String extractUserName(String token) {
        return extractAllClaims(token).get("name", String.class);
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String userLoginId) {
        final String username = extractUserLoginId(token);
        return (username.equals(userLoginId) && !isTokenExpired(token));
    }
}
