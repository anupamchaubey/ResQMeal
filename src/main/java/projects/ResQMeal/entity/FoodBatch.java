package projects.ResQMeal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FoodBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description; // e.g., "50 Loaves of Bread"
    private LocalDateTime expiryTime; // The exact time it goes bad
    private String status; // "AVAILABLE", "CLAIMED", or "EXPIRED"

    @Version
    private Integer version; // Our secret weapon for the interview

    // --- Empty Constructor required by Spring ---
    public FoodBatch() {
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getExpiryTime() { return expiryTime; }
    public void setExpiryTime(LocalDateTime expiryTime) { this.expiryTime = expiryTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}