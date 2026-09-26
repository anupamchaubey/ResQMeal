package projects.ResQMeal.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import projects.ResQMeal.dto.FoodRequest;
import projects.ResQMeal.entity.FoodBatch;
import projects.ResQMeal.service.FoodService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // Door 1: Restaurant posts food
    @PostMapping
    public FoodBatch postFood(@RequestBody FoodRequest request) {
        // Extract the email directly from the validated VIP pass
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return foodService.postFood(request.getDescription(), request.getExpiryTime(), userEmail);
    }

    // Door 2: Shelter checks the menu
    @GetMapping("/available")
    public List<FoodBatch> getAvailableFood() {
        return foodService.getAvailableFood();
    }

    // Door 3: Shelter claims a specific batch
    @PutMapping("/{id}/claim")
    public FoodBatch claimFood(@PathVariable Long id) {
        // Extract the email directly from the validated VIP pass
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return foodService.claimFood(id, userEmail);
    }

    // Door 4: Donor views their own active posts
    @GetMapping("/my-posts")
    public List<FoodBatch> getMyPosts() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return foodService.getMyPostedFood(userEmail);
    }

    // Door 5: Shelter views their past claims
    @GetMapping("/my-claims")
    public List<FoodBatch> getMyClaims() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return foodService.getMyClaimedFood(userEmail);
    }

    // Door 6: Donor cancels a post
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        foodService.deletePost(id, userEmail);
        return "Post deleted successfully.";
    }
}