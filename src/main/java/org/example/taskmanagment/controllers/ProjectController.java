package org.example.taskmanagment.controllers;

import jakarta.validation.Valid;
import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.Task;
import org.example.taskmanagment.services.ProjectService;
import org.example.taskmanagment.services.TaskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;


@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;
    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @PostMapping
    public Project createProject(@Valid @RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id, @Valid @RequestBody Project project) {
        return projectService.updateProject(id, project);
    }

    @GetMapping("/{id}")
    public Project getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }

    @GetMapping("/project/{id}/tasks")
    public List<Task> geTasksByProjectId(@PathVariable Long id) {
        return taskService.getTasksByProjectId(id);
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}