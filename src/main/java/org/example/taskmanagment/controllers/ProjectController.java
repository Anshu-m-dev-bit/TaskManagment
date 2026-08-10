package org.example.taskmanagment.controllers;

import jakarta.validation.Valid;
import org.example.taskmanagment.dto.project.ProjectMapper;
import org.example.taskmanagment.dto.project.request.*;
import org.example.taskmanagment.dto.project.response.ProjectResponse;
import org.example.taskmanagment.dto.project.response.ProjectUserResponse;
import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.Task;
import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.services.ProjectService;
import org.example.taskmanagment.services.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;


@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final ProjectMapper projectMapper;
    public ProjectController(ProjectService projectService, TaskService taskService, ProjectMapper projectMapper) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @PostMapping
    public ProjectResponse createProject(@Valid @RequestBody CreateProjectRequest projectDetails) {
        return projectMapper.toProjectResponse(projectService.createProject(projectDetails));
    }

    @PutMapping("/{id}")
    public ProjectResponse updateProject(@PathVariable Long id, @Valid @RequestBody UpdateProjectRequest projectDetails) {
        return projectMapper.toProjectResponse(projectService.updateProject(id, projectDetails));
    }

    @GetMapping("/{id}")
    public ProjectResponse getProject(@PathVariable Long id) {
        Project project = projectService.getProject(id);
        return projectMapper.toProjectResponse(project);
    }

    @GetMapping("/{id}/tasks")
    public Page<Task> geTasksByProjectId(@PathVariable Long id, @RequestParam (required = false) Integer page,
                                         @RequestParam (required = false) Integer size) {

        return taskService.getTasksByProjectId(id, page, size);
    }

    @GetMapping
    public Page<ProjectResponse> getAllProjects(@RequestParam (required = false) Integer page,
                                        @RequestParam (required = false) Integer size,
                                        @RequestParam (required = false) String sortField,
                                        @RequestParam (required = false) String sortDirection) {

        Page<Project> project =  projectService.getAllProjects(page, size, sortField, sortDirection);
        return project.map(
                projectMapper::toProjectResponse
        );
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @PutMapping("/{id}/users")
    public ProjectResponse replaceProjectMembers(@PathVariable Long id, @Valid @RequestBody ReplaceProjectMembersRequest projectDetails) {
        return projectMapper.toProjectResponse(projectService.replaceProjectMembers(id, projectDetails));
    }

    @PostMapping("/{id}/users")
    public ProjectResponse addProjectMembers(@PathVariable Long id, @Valid @RequestBody AddProjectMembersRequest projectDetails) {
        return projectMapper.toProjectResponse(projectService.addProjectMembers(id, projectDetails));
    }

    @DeleteMapping("/{id}/users")
    public ProjectResponse removeProjectMembers(@PathVariable Long id, @Valid @RequestBody RemoveProjectMembersRequest projectDetails) {
        return projectMapper.toProjectResponse(projectService.removeProjectMembers(id, projectDetails));
    }
}