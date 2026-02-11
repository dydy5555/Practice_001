package com.example.practice_01.config.jwt;

import com.example.practice_01.model.UserApp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class JwtUtils implements Serializable {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    public String generateToken (UserApp user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("phoneNumber", user.getPhoneNumber());
        claims.put("gmail", user.getGmail());
        claims.put("provider", user.getProvider());

        return Jwts.builder().setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("id", String.class);
    }
    // Retrieve Gmail from JWT token
    public String getGmailFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("gmail", String.class);
    }

    public String getPhoneNumberFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("phoneNumber", String.class);
    }

    public String getProviderFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("provider", String.class);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Retrieve expiration date from JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Validate token
    public Boolean validateToken(String token, UserApp user) {
        final String userId = getUserIdFromToken(token);
        final String phoneNumber = getPhoneNumberFromToken(token);
        final String provider = getProviderFromToken(token);

        // Check if the token is expired
        if (isTokenExpired(token)) {
            return false;
        }
        // Validate the username, phone number, and chat ID
        return  provider.equalsIgnoreCase(user.getProvider().toString()) &&
                userId.equalsIgnoreCase(String.valueOf(user.getId())) &&
                phoneNumber.equals(user.getPhoneNumber());
    }
}
