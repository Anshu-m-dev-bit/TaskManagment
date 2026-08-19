package org.example.taskmanagment;

import org.example.taskmanagment.config.SecurityConfig;
import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AdminSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (! userRepository.existsByRole(User.Role.ADMIN)) {
            User user = new User();
            user.setName("1stAdmin");
            user.setEmail("admin@123");
            user.setPassword(passwordEncoder.encode("adminPassw0rd"));
            user.setRole(User.Role.ADMIN);
            userRepository.save(user);
        }
    }
}
