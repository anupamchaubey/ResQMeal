package projects.ResQMeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.ResQMeal.entity.AppUser;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    // Spring automatically writes the SQL to find a user by their email
    Optional<AppUser> findByEmail(String email);
}