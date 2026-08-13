package org.example.taskmanagment.controllers;

import jakarta.annotation.Priority;
import jakarta.validation.Valid;
import org.example.taskmanagment.dto.task.TaskMapper;
import org.example.taskmanagment.dto.task.request.CreateTaskRequest;
import org.example.taskmanagment.dto.task.request.UpdateTaskRequest;
import org.example.taskmanagment.dto.task.response.TaskResponse;
import org.example.taskmanagment.entities.Task;
import org.example.taskmanagment.services.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest taskDetails) {
        return taskMapper.toTaskResponse(taskService.createTask(taskDetails));
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest taskDetails) {
        return taskMapper.toTaskResponse(taskService.updateTask(id, taskDetails));
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Long id) {
        return taskMapper.toTaskResponse(taskService.getTask(id));
    }

    @GetMapping
    public Page<TaskResponse> getAllTasks(@RequestParam (required = false) Task.CurrStatus status,
                                  @RequestParam (required = false) Task.CurrPriority priority,
                                  @RequestParam (required = false) Integer page,
                                  @RequestParam (required = false) Integer size,
                                  @RequestParam (required = false) String sortField,
                                  @RequestParam (required = false) String sortDirection) {
        Page<Task> tasks = taskService.getAllTasks(status, priority, page, size, sortField, sortDirection);
        return tasks.map(
                taskMapper::toTaskResponse
        );
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}