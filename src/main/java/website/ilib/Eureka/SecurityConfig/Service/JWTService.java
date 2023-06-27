package website.ilib.Eureka.SecurityConfig.Service;

import java.security.Key;
import java.util.Date;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mongodb.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import website.ilib.Eureka.Team.Model.TeamModel;


@Service
public class JWTService {

    @Value("${secret.key}")
    private String SECRET_KEY;

    private static final long EXPIRE_DURATION = 2 * 60 * 60 * 1000;

    public String generateAccessToken(TeamModel teamModel) {
        return Jwts.builder()
                .setSubject(teamModel.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
                 
    }
    private Claims extractAllClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }
    public String extractUserName(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }
    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }
    private boolean isTokenExpire(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String email = extractUserName(jwt);
        return (email.equals(userDetails.getUsername())) && !isTokenExpire(jwt);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}

