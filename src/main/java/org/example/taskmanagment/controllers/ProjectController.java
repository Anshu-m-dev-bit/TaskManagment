package org.example.taskmanagment.controllers;

import jakarta.validation.Valid;
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
    public ProjectResponse getProject(@PathVariable Long id) {
        Project project = projectService.getProject(id);
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setProjectId(id);
        projectResponse.setName(project.getName());
        projectResponse.setDescription(project.getDescription());
        Set<User> users = project.getUsers();
        System.out.println(users.size());
        Set<ProjectUserResponse> projectUserResponse = new HashSet<>();
        for (User user: users) {
            ProjectUserResponse userResponse = new ProjectUserResponse();
            userResponse.setUserId(user.getId());
            userResponse.setName(user.getName());
            projectUserResponse.add(userResponse);
        }
        projectResponse.setProjectUserResponse(projectUserResponse);
        return projectResponse;
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
                project1 -> {
                    ProjectResponse projectResponse = new ProjectResponse();
                    projectResponse.setProjectId(project1.getId());
                    projectResponse.setName(project1.getName());
                    projectResponse.setDescription(project1.getDescription());
                    Set<User> users = project1.getUsers();
                    Set<ProjectUserResponse> projectUserResponses= new HashSet<>();
                    for (User user: users) {
                        ProjectUserResponse userResponse = new ProjectUserResponse();
                        userResponse.setUserId(user.getId());
                        userResponse.setName(user.getName());
                        projectUserResponses.add(userResponse);
                    }
                    projectResponse.setProjectUserResponse(projectUserResponses);
                    return projectResponse;
                }
        );
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