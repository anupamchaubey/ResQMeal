package projects.ResQMeal.service;

import org.springframework.stereotype.Service;
import projects.ResQMeal.entity.FoodBatch;
import projects.ResQMeal.repository.FoodBatchRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FoodService {

    private final FoodBatchRepository repository;

    // We use a constructor to bring in the repository instead of using @Autowired.
    public FoodService(FoodBatchRepository repository) {
        this.repository = repository;
    }

    // Rule 1: A restaurant posts new food
    public FoodBatch postFood(String description, LocalDateTime expiryTime) {
        FoodBatch batch = new FoodBatch();
        batch.setDescription(description);
        batch.setExpiryTime(expiryTime);
        batch.setStatus("AVAILABLE");
        return repository.save(batch);
    }

    // Rule 2: A shelter looks for fresh food
    public List<FoodBatch> getAvailableFood() {
        return repository.findByStatusAndExpiryTimeAfter("AVAILABLE", LocalDateTime.now());
    }

    // Rule 3: A shelter claims the food (The double-booking defense)
    public FoodBatch claimFood(Long batchId) {
        FoodBatch batch = repository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Food batch does not exist."));

        if (!batch.getStatus().equals("AVAILABLE")) {
            throw new RuntimeException("This food is no longer available.");
        }

        batch.setStatus("CLAIMED");

        // When we save here, the @Version tag we added earlier checks the database.
        // If another shelter claimed this milliseconds ago, this save() will fail and throw an error.
        return repository.save(batch);
    }
}