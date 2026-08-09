package org.example.taskmanagment.controllers;

import jakarta.validation.Valid;
import org.example.taskmanagment.dto.project.request.*;
import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.Task;
import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.services.ProjectService;
import org.example.taskmanagment.services.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;


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
    public Project createProject(@Valid @RequestBody CreateProjectRequest projectDetails) {
        return projectService.createProject(projectDetails);
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id, @Valid @RequestBody UpdateProjectRequest projectDetails) {
        return projectService.updateProject(id, projectDetails);
    }

    @GetMapping("/{id}")
    public Project getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }

    @GetMapping("/{id}/tasks")
    public Page<Task> geTasksByProjectId(@PathVariable Long id, @RequestParam (required = false) Integer page,
                                         @RequestParam (required = false) Integer size) {

        return taskService.getTasksByProjectId(id, page, size);
    }

    @GetMapping
    public Page<Project> getAllProjects(@RequestParam (required = false) Integer page,
                                        @RequestParam (required = false) Integer size,
                                        @RequestParam (required = false) String sortField,
                                        @RequestParam (required = false) String sortDirection) {
        return projectService.getAllProjects(page, size, sortField, sortDirection);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @PutMapping("/{id}/users")
    public Project replaceProjectMembers(@PathVariable Long id, @Valid @RequestBody ReplaceProjectMembersRequest projectDetails) {
        return projectService.replaceProjectMembers(id, projectDetails);
    }

    @PostMapping("/{id}/users")
    public Project addProjectMembers(@PathVariable Long id, @Valid @RequestBody AddProjectMembersRequest projectDetails) {
        return projectService.addProjectMembers(id, projectDetails);
    }

    @DeleteMapping("/{id}/users")
    public Project removeProjectMembers(@PathVariable Long id, @Valid @RequestBody RemoveProjectMembersRequest projectDetails) {
        return projectService.removeProjectMembers(id, projectDetails);
    }
}