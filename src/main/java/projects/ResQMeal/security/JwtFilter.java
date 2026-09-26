package projects.ResQMeal.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import projects.ResQMeal.repository.TokenBlacklistRepository;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    public JwtFilter(JwtUtil jwtUtil, TokenBlacklistRepository tokenBlacklistRepository) {
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistRepository=tokenBlacklistRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 1. Check if they brought a pass in their header
        String header = request.getHeader("Authorization");

        // If there's no pass, let them proceed (they might be heading to the public Login door)
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Grab the actual token text (skipping the word "Bearer ")
        String token = header.substring(7);

        // NEW CHECK: Is this token on the blacklist?
        if (tokenBlacklistRepository.existsByToken(token)) {
            // If it is blacklisted, throw them out immediately.
            chain.doFilter(request, response);
            return;
        }

        // 3. Hand it to our JwtUtil to scan it
        if (jwtUtil.validateToken(token)) {
            String email = jwtUtil.extractEmail(token);

            // 4. Tell Spring Security: "This person is legitimate, let them in."
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // Send them to their destination
        chain.doFilter(request, response);
    }
}