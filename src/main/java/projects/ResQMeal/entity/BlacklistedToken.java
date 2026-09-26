package projects.ResQMeal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BlacklistedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 512)
    private String token; // The dead JWT string

    private LocalDateTime blacklistedAt;

    public BlacklistedToken() {}
    public BlacklistedToken(String token) {
        this.token = token;
        this.blacklistedAt = LocalDateTime.now();
    }

    public String getToken() { return token; }
    // standard getters and setters...
}