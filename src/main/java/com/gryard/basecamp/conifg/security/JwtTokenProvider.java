package com.gryard.basecamp.conifg.security;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gryard.basecamp.dto.TokenInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	private long accessTokenValidMilisecond = 1000L * 60 * 60 * 24;
	private long refreshTokenValidMilisecond = 1000L * 60 * 60 * 24;
	
	private final Key key;
	 
    public JwtTokenProvider(@Value("${spring.jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
 
    // ���� ������ ������ AccessToken, RefreshToken �� �����ϴ� �޼���
    public TokenInfo generateToken(Authentication authentication) {
    	
        // ���� ��������
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
 
        long now = (new Date()).getTime();
        
        // Access Token ����
        Date accessTokenExpiresIn = new Date(now + accessTokenValidMilisecond);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
 
        // Refresh Token ����
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + refreshTokenValidMilisecond))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
 
        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    
    // JWT ��ū�� ��ȣȭ�Ͽ� ��ū�� ����ִ� ������ ������ �޼���
    public Authentication getAuthentication(String accessToken) {
        // ��ū ��ȣȭ
        Claims claims = parseClaims(accessToken);
 
        if (claims.get("auth") == null) {
            throw new RuntimeException("���� ������ ���� ��ū�Դϴ�.");
        }
 
        // Ŭ���ӿ��� ���� ���� ��������
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
 
        // UserDetails ��ü�� ���� Authentication ����
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
    
    // ��ū ������ �����ϴ� �޼���
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
    
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
