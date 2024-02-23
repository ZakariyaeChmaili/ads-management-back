package com.virtuocode.adsmanagementback.services.jwtService;

import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.services.UserService.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtService {

    private final String SECRET_KEY = "77397A244326462948404D635166546xdsfvdgvA576E5A7234753778214125442A472D4B";
    private final Long TOKEN_LIFE_TIME = TimeUnit.MINUTES.toMillis(30);
//    private final Long TOKEN_LIFE_TIME = TimeUnit.SECONDS.toMillis(10);

    private final UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    public String generateToken(User userDetails) {
        return Jwts
                .builder()
                .claim("userId", userDetails.getId())
                .claim("role", userDetails.getRole().name())
                .claim("username", userDetails.getUsername())
                .claim("tokenLifeTime", TOKEN_LIFE_TIME)

                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.SECRET_KEY)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractClaimFromToken(String token, String claim) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get(claim, String.class);
    }


    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean checkTokenExpiration(User user) {
        long currentDate = new Date().getTime();
        long lastActivity = user.getLastActivity().getTime();
        long res = currentDate - lastActivity;
        return res <= TOKEN_LIFE_TIME;
    }

    public User getUserFromToken(String token) {
        String username = extractClaimFromToken(token, "username");
        return this.userService.loadUserByUsername(username);

    }
}
