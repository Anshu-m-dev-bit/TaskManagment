package org.example.taskmanagment.repositories;

import org.example.taskmanagment.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
