package org.example.taskmanagment.repositories;

import org.example.taskmanagment.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
