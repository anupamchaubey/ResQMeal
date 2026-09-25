package projects.ResQMeal.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import projects.ResQMeal.entity.FoodBatch;
import projects.ResQMeal.repository.FoodBatchRepository;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class FoodSweeper {

    private final FoodBatchRepository repository;

    public FoodSweeper(FoodBatchRepository repository) {
        this.repository = repository;
    }

    // This tells Spring to run this exact block of code every 60 seconds
    @Scheduled(fixedRate = 60000)
    public void cleanUpExpiredFood() {

        // 1. Ask the database for all available food that is past its time
        List<FoodBatch> expiredBatches = repository.findByStatusAndExpiryTimeBefore(
                "AVAILABLE", LocalDateTime.now()
        );

        // 2. Go through the list one by one and change the status
        for (FoodBatch batch : expiredBatches) {
            batch.setStatus("EXPIRED");
            repository.save(batch);
        }
    }
}