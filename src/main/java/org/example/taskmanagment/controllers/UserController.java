package org.example.taskmanagment.controllers;

import jakarta.validation.Valid;
import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.Task;
import org.example.taskmanagment.entities.User;

import java.util.List;

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
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public Page<User> getAllUsers(@RequestParam (required = false) Integer page,
                                  @RequestParam (required = false) Integer size) {
        return userService.getAllUsers(page, size);
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