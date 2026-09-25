package projects.ResQMeal.dto;

import java.time.LocalDateTime;

// Standard Java Class acting as our delivery box (DTO)
public class FoodRequest {
    private String description;
    private LocalDateTime expiryTime;

    // Empty constructor needed for Spring to unpack the JSON data
    public FoodRequest() {}

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getExpiryTime() { return expiryTime; }
    public void setExpiryTime(LocalDateTime expiryTime) { this.expiryTime = expiryTime; }
}