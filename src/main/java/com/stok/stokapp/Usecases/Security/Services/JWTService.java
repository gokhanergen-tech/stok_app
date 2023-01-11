package com.stok.stokapp.Usecases.Security.Services;

import com.stok.stokapp.Usecases.Security.Entities.Authority;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTService {
    public String generateJWT(String secretKey, Integer expiration, Authentication authentication){
        return Jwts.builder().
                setSubject(authentication.getName())
                .claim("authorities",getAuthorities(authentication.getAuthorities()))
                .setIssuedAt(new Date()).setExpiration(Date.from(LocalDateTime.now().plusSeconds(expiration).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public String extractUserName(String token,String secretKey){
        return  Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    private List<String> getAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities){
        return grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
