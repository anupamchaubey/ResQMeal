package projects.ResQMeal.service;

import org.springframework.stereotype.Service;
import projects.ResQMeal.entity.AppUser;
import projects.ResQMeal.entity.FoodBatch;
import projects.ResQMeal.repository.FoodBatchRepository;
import projects.ResQMeal.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FoodService {

    private final FoodBatchRepository batchRepository;
    private final UserRepository userRepository;

    // We use a constructor to bring in the repository instead of using @Autowired.
    public FoodService(FoodBatchRepository batchRepository, UserRepository userRepository) {
        this.batchRepository = batchRepository;
        this.userRepository=userRepository;
    }

    // Rule 1: A restaurant posts new food
    public FoodBatch postFood(String description, LocalDateTime expiryTime, String donorEmail) {
        // 1. Fetch the exact user from the database using their email
        AppUser donor = userRepository.findByEmail(donorEmail)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        FoodBatch batch = new FoodBatch();
        batch.setDescription(description);
        batch.setExpiryTime(expiryTime);
        batch.setStatus("AVAILABLE");

        // 2. Attach the user to the food batch
        batch.setDonor(donor);

        return batchRepository.save(batch);
    }

    // Rule 2: A shelter looks for fresh food
    public List<FoodBatch> getAvailableFood() {
        return repository.findByStatusAndExpiryTimeAfter("AVAILABLE", LocalDateTime.now());
    }

    // Rule 3: A shelter claims the food (The double-booking defense)
    public FoodBatch claimFood(Long batchId, String claimerEmail) {
        FoodBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Food batch does not exist."));

        if (!batch.getStatus().equals("AVAILABLE")) {
            throw new RuntimeException("This food is no longer available.");
        }

        // 1. Fetch the shelter from the database
        AppUser claimer = userRepository.findByEmail(claimerEmail)
                .orElseThrow(() -> new RuntimeException("Shelter not found"));

        // 2. Attach the shelter to the batch
        batch.setClaimer(claimer);
        batch.setStatus("CLAIMED");

        return batchRepository.save(batch);
    }

    // --- Fetch History ---
    public List<FoodBatch> getMyPostedFood(String email) {
        AppUser donor = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return batchRepository.findByDonor(donor);
    }

    public List<FoodBatch> getMyClaimedFood(String email) {
        AppUser claimer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return batchRepository.findByClaimer(claimer);
    }

    // --- Secure Deletion ---
    public void deletePost(Long batchId, String email) {
        FoodBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Food batch does not exist."));

        // Security Check: Does the person trying to delete this actually own it?
        if (!batch.getDonor().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized: You can only delete your own posts.");
        }

        // Logic Check: You cannot delete food that a shelter is already driving to pick up
        if (batch.getStatus().equals("CLAIMED")) {
            throw new RuntimeException("Cannot delete: This food has already been claimed.");
        }

        batchRepository.delete(batch);
    }
}