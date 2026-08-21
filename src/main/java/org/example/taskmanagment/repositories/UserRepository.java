package org.example.taskmanagment.repositories;

import org.example.taskmanagment.entities.User;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByEmail(String email);
    public boolean existsByRole(User.Role role);
    public Optional<User> findByEmail(String email);
}
