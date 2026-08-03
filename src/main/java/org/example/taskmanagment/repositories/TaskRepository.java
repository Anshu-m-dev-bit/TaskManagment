package org.example.taskmanagment.repositories;

import org.example.taskmanagment.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public Page<Task> findAllByProjectId(Long id, Pageable pageRequest);
    public Page<Task> findAllByUserId(Long id, Pageable pageRequest);
    public Page<Task> findAllByStatus(Task.CurrStatus status, Pageable pageRequest);
    public Page<Task> findAllByPriority(Task.CurrPriority priority, Pageable pageRequest);
    public Page<Task> findAllByStatusAndPriority(Task.CurrStatus status, Task.CurrPriority priority, Pageable pageRequest);

    @Query("SELECT T FROM Task T " +
            "ORDER BY CASE T.priority " +
            "WHEN org.example.taskmanagment.entities.Task.CurrPriority.HIGH THEN 3 " +
            "WHEN org.example.taskmanagment.entities.Task.CurrPriority.MEDIUM THEN 2 " +
            "WHEN org.example.taskmanagment.entities.Task.CurrPriority.LOW THEN 1 " +
            "END")
    public Page<Task> findAllTasksByPriority(Pageable pageRequest);

    @Query("SELECT T FROM Task T " +
            "WHERE T.status = ?1 ORDER BY CASE T.priority " +
            "WHEN org.example.taskmanagment.entities.Task.CurrPriority.HIGH THEN 3 " +
            "WHEN org.example.taskmanagment.entities.Task.CurrPriority.MEDIUM THEN 2 " +
            "WHEN org.example.taskmanagment.entities.Task.CurrPriority.LOW THEN 1 " +
            "END")
    public Page<Task> findAllByStatusOrderByPriority(Task.CurrStatus status, Pageable pageRequest);
}
