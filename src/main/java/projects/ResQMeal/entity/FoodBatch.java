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

    // Many batches of food can belong to One donor
    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private AppUser donor;

    // Many batches of food can be claimed by One shelter
    @ManyToOne
    @JoinColumn(name = "claimer_id")
    private AppUser claimer;

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

    public AppUser getDonor() {
        return donor;
    }

    public void setDonor(AppUser donor) {
        this.donor = donor;
    }

    public AppUser getClaimer() {
        return claimer;
    }

    public void setClaimer(AppUser claimer) {
        this.claimer = claimer;
    }
}