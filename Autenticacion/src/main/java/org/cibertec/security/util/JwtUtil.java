package org.cibertec.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.cibertec.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long expirationMs;
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // En JwtUtil.java
    public String generateTokenWithUserInfo(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();

        // Agregar los IDs que te pidieron
        claims.put("idUsuario", usuario.getIdUsuario());
        claims.put("idRol", usuario.getRol() != null ? usuario.getRol().getIdRol() : null);
        claims.put("rol", usuario.getRol() != null ? "ROLE_" + usuario.getRol().getNombreRol() : "ROLE_ANONIMO");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClains(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClains(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClains(token).getExpiration();
    }

    public boolean validateToken(String token, UserDetails user) {
        String username = extractUsername(token);
        Date expiration = extractExpiration(token);
        return username.equals(user.getUsername()) &&
                expiration.after(new Date());
    }
}
