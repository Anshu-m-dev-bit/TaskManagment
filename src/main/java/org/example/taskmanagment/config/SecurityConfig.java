package org.example.taskmanagment.config;

import org.example.taskmanagment.security.CustomAccessDeniedHandler;
import org.example.taskmanagment.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                request ->
                        request.requestMatchers("/auth/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("USER", "MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/projects").hasAnyRole("USER", "MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/projects").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/projects").hasAnyRole("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/projects").hasAnyRole("MANAGER", "ADMIN")

                                .requestMatchers(HttpMethod.GET, "/tasks").hasAnyRole("USER", "MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/tasks").hasAnyRole("USER", "MANAGER", "ADMIN")

                                .anyRequest().authenticated()
                                ).exceptionHandling(error ->
                        error.accessDeniedHandler(new CustomAccessDeniedHandler()));


        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider configuration) {
        return new ProviderManager(configuration);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(CustomUserDetailsService customUserDetailsService,
                                                               PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider configuration = new DaoAuthenticationProvider(customUserDetailsService);
        configuration.setPasswordEncoder(passwordEncoder);
        return configuration;
    }


}
