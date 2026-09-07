package org.example.taskmanagment;


import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${ADMIN_SEEDER_ADMIN_PASSWORD}")
    private String adminPassword;

    @Value("${ADMIN_SEEDER_MANAGER_PASSWORD}")
    private String managerPassword;

    @Value("${ADMIN_SEEDER_USER_PASSWORD}")
    private String userPassword;

    @Override
    public void run(String... args) {
        if (! userRepository.existsByRole(User.Role.ADMIN)) {
            User user = new User();
            user.setName("1stAdmin");
            user.setEmail("admin@123");
            user.setPassword(passwordEncoder.encode(adminPassword));
            user.setRole(User.Role.ADMIN);
            userRepository.save(user);
        }
        if (! userRepository.existsByRole(User.Role.MANAGER)) {
            User user = new User();
            user.setName("1stManager");
            user.setEmail("manager@123");
            user.setPassword(passwordEncoder.encode(managerPassword));
            user.setRole(User.Role.MANAGER);
            userRepository.save(user);
        }
        if (! userRepository.existsByRole(User.Role.USER)) {
            User user = new User();
            user.setName("1stUser");
            user.setEmail("user@123");
            user.setPassword(passwordEncoder.encode(userPassword));
            user.setRole(User.Role.USER);
            userRepository.save(user);
        }
    }
}
