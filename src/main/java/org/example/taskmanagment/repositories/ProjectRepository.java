package org.example.taskmanagment.repositories;

import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    public List<Project> findAllByUserId(Long id);
}
