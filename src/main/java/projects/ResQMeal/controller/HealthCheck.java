package projects.ResQMeal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/")
    public String health(){
        return "yoo! the backend is running and APIs are working fine ✨";
    }
}
