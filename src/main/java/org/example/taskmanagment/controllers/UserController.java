package org.example.taskmanagment.controllers;

import jakarta.validation.Valid;
import org.example.taskmanagment.dto.user.request.CreateUserRequest;
import org.example.taskmanagment.dto.user.request.UpdateUserRequest;
import org.example.taskmanagment.dto.user.response.UserProjectResponse;
import org.example.taskmanagment.dto.user.response.UserResponse;
import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.Task;
import org.example.taskmanagment.entities.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.example.taskmanagment.services.ProjectService;
import org.example.taskmanagment.services.TaskService;
import org.example.taskmanagment.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    public UserController(UserService userService, ProjectService projectService, TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody CreateUserRequest userDetails) {
        return userService.createUser(userDetails);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmailID(user.getEmail());
        Set<Project> projects = user.getProjects();
        Set<UserProjectResponse> userProjectResponses = new HashSet<>();
        for (Project project: projects) {
            UserProjectResponse userProjectResponse = new UserProjectResponse();
            userProjectResponse.setProjectId(project.getId());
            userProjectResponse.setProjectName(project.getName());
            userProjectResponses.add(userProjectResponse);
        }
        userResponse.setUserProjectResponses(userProjectResponses);
        return userResponse;
    }

    @GetMapping
    public Page<UserResponse> getAllUsers(@RequestParam (required = false) Integer page,
                                  @RequestParam (required = false) Integer size,
                                  @RequestParam (required = false) String sortField,
                                  @RequestParam (required = false) String sortDirection) {
        Page<User> users = userService.getAllUsers(page, size, sortField, sortDirection);
        return users.map(
                user1 -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setUserId(user1.getId());
                    userResponse.setName(user1.getName());
                    userResponse.setEmailID(user1.getEmail());
                    Set<Project> projects = user1.getProjects();
                    Set<UserProjectResponse> userProjectResponses = new HashSet<>();
                    for (Project project: projects) {
                        UserProjectResponse userProjectResponse = new UserProjectResponse();
                        userProjectResponse.setProjectId(project.getId());
                        userProjectResponse.setProjectName(project.getName());
                        userProjectResponses.add(userProjectResponse);
                    }
                    userResponse.setUserProjectResponses(userProjectResponses);
                    return userResponse;
                }
        );

    }

    @GetMapping("/{id}/projects")
    public List<Project> getProjectsByUserId(@PathVariable Long id) {
        return projectService.getProjectsByUserId(id);
    }

    @GetMapping("/{id}/tasks")
    public Page<Task> geTasksByUserId(@PathVariable Long id, @RequestParam (required = false) Integer page,
                                      @RequestParam (required = false) Integer size) {
        return taskService.getTasksByUserId(id, page, size);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}