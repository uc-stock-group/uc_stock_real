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
        long now = nowDate.getTime();
        long validityInMilliseconds = 1000 * 60 * 60 * 12; // 12시간

//        access token 생성
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(nowDate)
                .setExpiration(new Date(now + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS512, secret)
                .setIssuer("uc_stock")
                .claim("name", user.getUsername())
                .claim("stock_access_token", stockToken.getAccess_token())
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

    public String extractUsername(String token) {
        return extractAllClaims(token).get("name", String.class);
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }
}
