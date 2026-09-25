package projects.ResQMeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ResQMealApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResQMealApplication.class, args);
	}

}
