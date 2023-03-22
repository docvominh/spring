package com.vominh.example.spring.mongo.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtService {


    private final String secret;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String createToken(AppUserDetails userDetails) {
        var expiredDate = Date.from(LocalDate.now().plusDays(60).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        var information = new HashMap<String, Object>();
        information.put("id", userDetails.getUser().getUserId());
        information.put("name", userDetails.getUser().getName());
        information.put("email", userDetails.getUser().getEmail());

        return Jwts.builder()
                .setClaims(information)
                .setSubject(userDetails.getUser().getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    private List<String> parseToken(String token) {
        return List.of(token.split("\\."));
    }
}
