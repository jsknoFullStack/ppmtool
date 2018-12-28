package com.jskno.ppmtoolbe.security;

import com.jskno.ppmtoolbe.security.domain.User;
import com.jskno.ppmtoolbe.security.utils.SecurityConstants;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }


    public boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
            return true;
        } catch(SignatureException ex) {
            System.out.println("Invalid JWT Signature");
        } catch(MalformedJwtException ex) {
            System.out.println("Invalid JWT Token");
        } catch(ExpiredJwtException ex) {
            System.out.println("Expired JWT Token");
        } catch(UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT Token");
        } catch(IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }
}
