package org.example.taskmanagment.config;

import org.example.taskmanagment.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                request ->
                        request.requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated());

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
