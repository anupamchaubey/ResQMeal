package projects.ResQMeal.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // This creates a highly secure, randomized secret lock that ONLY your server knows.
    // If a hacker tries to create their own fake VIP pass, this lock will reject it.
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Job 1: Print the pass
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                // Pass expires in exactly 10 hours
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key)
                .compact();
    }

    // Job 2: Read the name on the pass
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Job 3: Check if the pass is real or expired
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false; // If the pass is fake or expired, throw them out
        }
    }
}