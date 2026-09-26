package projects.ResQMeal.dto;

// --- Delivery Boxes (DTOs) for incoming data ---
public class AuthRequest {
    private String email;
    private String password;
    private String role; // Needed for registration (e.g., "NGO" or "DONOR")

    public AuthRequest() {}
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}