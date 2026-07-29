package org.example.taskmanagment.repositories;

import org.example.taskmanagment.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public Page<Task> findAllByProjectId(Long id, Pageable pageRequest);
    public Page<Task> findAllByUserId(Long id, Pageable pageRequest);
    public Page<Task> findAllByStatus(Task.CurrStatus status, Pageable pageRequest);
    public Page<Task> findAllByPriority(Task.CurrPriority priority, Pageable pageRequest);
    public Page<Task> findAllByStatusAndPriority(Task.CurrStatus status, Task.CurrPriority priority, Pageable pageRequest);
}
