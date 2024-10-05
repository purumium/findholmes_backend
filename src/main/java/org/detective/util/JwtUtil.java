package org.detective.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.detective.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY; // 비밀 키

    private final long EXPIRATION_TIME = 86400000; // 1일

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        // Principal에서 CustomUserDetails 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // userId 가져오기
        Long userId = userDetails.getUserId();

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 권한을 문자열 목록으로 변환
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authorities)
                .claim("id", userId)
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String generateToken2(User user) {
        String username = user.getEmail();

        // userId 가져오기
        Long userId = user.getUserId();

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 권한을 문자열 목록으로 변환
        String authorities = user.getRole();

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authorities)
                .claim("id", userId)
                .claim("social","google")
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
