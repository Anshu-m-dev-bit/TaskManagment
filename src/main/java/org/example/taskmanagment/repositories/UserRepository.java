package org.example.taskmanagment.repositories;

import org.example.taskmanagment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
