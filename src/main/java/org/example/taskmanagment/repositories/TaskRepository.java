package org.example.taskmanagment.repositories;

import org.example.taskmanagment.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findAllByProjectId(Long id);
    public List<Task> findAllByStatus(Task.CurrStatus status);
    public List<Task> findAllByPriority(Task.CurrPriority priority);
    public List<Task> findAllByStatusAndPriority(Task.CurrStatus status, Task.CurrPriority priority);
}
