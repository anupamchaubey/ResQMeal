package projects.ResQMeal.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
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