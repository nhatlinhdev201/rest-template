package com.example.apigateway.util;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
public boolean validateToken(String token) {
    try {
        // Parse token
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();

        // Kiểm tra thuật toán ký
        if (!claimsJws.getHeader().getAlgorithm().equals(SignatureAlgorithm.HS512.getValue())) {
            logger.error("Invalid token algorithm");
            return false;
        }

        // Kiểm tra thời gian hết hạn của token
        if (claims.getExpiration().before(new Date())) {
            logger.error("Token has expired");
            return false;
        }

        // Các kiểm tra khác có thể bổ sung tại đây, ví dụ: kiểm tra trường payload, kiểm tra tính duy nhất của token, kiểm tra loại token, v.v.

        return true; // Token hợp lệ
    } catch (ExpiredJwtException ex) {
        logger.error("Token has expired");
        return false;
    } catch (JwtException | IllegalArgumentException ex) {
        logger.error("Invalid token: " + ex.getMessage());
        return false;
    }
}

}
