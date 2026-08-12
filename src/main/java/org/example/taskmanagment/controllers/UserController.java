package org.example.taskmanagment.controllers;

import jakarta.validation.Valid;
import org.example.taskmanagment.dto.project.ProjectMapper;
import org.example.taskmanagment.dto.project.response.ProjectResponse;
import org.example.taskmanagment.dto.user.UserMapper;
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
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;
    public UserController(UserService userService, ProjectService projectService,
                          TaskService taskService, UserMapper userMapper, ProjectMapper projectMapper) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest userDetails) {
        return userMapper.toUserResponse(userService.createUser(userDetails));
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest userDetails) {
        return userMapper.toUserResponse(userService.updateUser(id, userDetails));
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userMapper.toUserResponse(userService.getUser(id));
    }

    @GetMapping
    public Page<UserResponse> getAllUsers(@RequestParam (required = false) Integer page,
                                  @RequestParam (required = false) Integer size,
                                  @RequestParam (required = false) String sortField,
                                  @RequestParam (required = false) String sortDirection) {
        Page<User> users = userService.getAllUsers(page, size, sortField, sortDirection);
        return users.map(
                userMapper::toUserResponse
        );

    }

    @GetMapping("/{id}/projects")
    public Page<ProjectResponse> getProjectsByUserId(@PathVariable Long id,
                                                     @RequestParam (required = false) Integer page,
                                                     @RequestParam (required = false) Integer size,
                                                     @RequestParam (required = false) String sortField,
                                                     @RequestParam (required = false) String sortDirection) {
        Page<Project> project = projectService.getProjectsByUserId(id, page, size, sortField, sortDirection);
        return project.map(
                projectMapper::toProjectResponse
        );
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