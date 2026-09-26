package projects.ResQMeal.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import projects.ResQMeal.dto.AuthRequest;
import projects.ResQMeal.entity.AppUser;
import projects.ResQMeal.repository.UserRepository;
import projects.ResQMeal.security.JwtUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Door 1: Registration
    @PostMapping("/register")
    public String registerUser(@RequestBody AuthRequest request) {
        // 1. Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already taken.");
        }

        // 2. Create the user and scramble the password
        AppUser newUser = new AppUser();
        newUser.setEmail(request.getEmail());
        // We never save the raw password. We hash it with BCrypt first.
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(request.getRole());

        // 3. Save to database
        userRepository.save(newUser);
        return "User registered successfully.";
    }

    // Door 2: Login
    @PostMapping("/login")
    public String loginUser(@RequestBody AuthRequest request) {
        // 1. Find the user by email
        AppUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found."));

        // 2. Manually check if the typed password matches the scrambled database password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        // 3. If they match, print and hand over the VIP pass
        return jwtUtil.generateToken(user.getEmail());
    }
}