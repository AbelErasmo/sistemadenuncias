package com.sistemadenuncias.sistemadenuncias.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String secret = "segredo-super-seguro";

    public String gerarAccessToken(UserDetails userDetails) {
        // 15 min
        long accessTokenExp = 15 * 60 * 1000;
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExp))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String gerarRefreshToken(UserDetails userDetails) {
        // 7 dias
        long refreshTokenExp = 7 * 24 * 60 * 60 * 1000;
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExp))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String extrairUsername(String token) {
        return Jwts.parser()
                .setSigningKey((secret.getBytes()))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validarToken(String token, UserDetails userDetails) {
        String username = extrairUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpirado(token);
    }

    private boolean isExpirado(String token) {
        Date exp = Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return exp.before(new Date());
    }
}

