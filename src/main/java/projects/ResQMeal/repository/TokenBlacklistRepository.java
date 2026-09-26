package projects.ResQMeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.ResQMeal.entity.BlacklistedToken;

import java.util.Optional;

public interface TokenBlacklistRepository extends JpaRepository<BlacklistedToken, Long> {
    boolean existsByToken(String token); // Returns true if the pass is dead
}