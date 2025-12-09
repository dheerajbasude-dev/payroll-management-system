package com.dheeraj.payroll.security;

import com.dheeraj.payroll.exception.LoginException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.token.validity}")
    private long JWT_TOKEN_VALIDITY;

    @Value("${jwt.secret}")
    private String secret;

    private final Map<String, String> activeTokens = new ConcurrentHashMap<>();


    /*---CORRECT SIGNING KEY METHOD (HEX SECRET)---*/
    private java.security.Key getSigningKey() {
        byte[] keyBytes = HexFormat.of().parseHex(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();

        if (activeTokens.containsKey(username)) {
            String existingToken = activeTokens.get(username);
            if (!isTokenExpired(existingToken)) {
                throw new LoginException("User already logged in.", existingToken);
            }
        }

        Map<String, Object> claims = new HashMap<>();
        String token = doGenerateToken(claims, username);

        activeTokens.put(username, token);
        return token;
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public void invalidateToken(String token) {
        activeTokens.remove(getUsernameFromToken(token));
    }

    public JwtParser jwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .setAllowedClockSkewSeconds(60)
                .build();
    }


}


