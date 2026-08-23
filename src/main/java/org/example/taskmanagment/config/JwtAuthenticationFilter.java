package org.example.taskmanagment.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.taskmanagment.security.CustomUserDetailsService;
import org.example.taskmanagment.services.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("========== JWT FILTER REACHED ==========");
        String header = request.getHeader("Authorization");
        System.out.println(header);
        if(header != null && header.startsWith("Bearer ")) {
            System.out.println(header);
            String token = header.substring(7);
            if (jwtService.validateToken(token)) {
                String username = jwtService.extractUsername(token);
                UserDetails verifiedUser = customUserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new
                        UsernamePasswordAuthenticationToken(verifiedUser,
                        null,
                        verifiedUser.getAuthorities());

                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authenticationToken);
                System.out.println(SecurityContextHolder.getContext().getAuthentication());
            }
        }
        System.out.println("========== JWT FILTER ENDED ==========");
        filterChain.doFilter(request, response);
    }
}
