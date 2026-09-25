package projects.ResQMeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.ResQMeal.entity.FoodBatch;
import java.time.LocalDateTime;
import java.util.List;

public interface FoodBatchRepository extends JpaRepository<FoodBatch, Long> {

    // Spring looks at this exact method name and automatically writes a query
    // to find food where status = "AVAILABLE" and time is not expired.
    List<FoodBatch> findByStatusAndExpiryTimeAfter(String status, LocalDateTime currentTime);
}