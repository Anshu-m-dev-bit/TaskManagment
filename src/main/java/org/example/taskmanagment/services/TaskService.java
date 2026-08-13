package org.example.taskmanagment.services;

import org.example.taskmanagment.dto.task.request.CreateTaskRequest;
import org.example.taskmanagment.dto.task.request.UpdateTaskRequest;
import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.Task;
import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.exceptions.*;
import org.example.taskmanagment.repositories.ProjectRepository;
import org.example.taskmanagment.repositories.TaskRepository;
import org.example.taskmanagment.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TaskService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public TaskService(UserRepository userRepository, ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    private static final Set<String> allowedSortFields = new HashSet<>(Arrays.asList("id", "title", "createdAt", "dueDate", "priority"));

    public Task createTask(CreateTaskRequest taskDetails) {
        Long userId = taskDetails.getUserId();
        Long projectId = taskDetails.getProjectId();

        Task task = new Task();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        task.setUser(user);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + projectId + " not found"));
        task.setProject(project);

        Set<User> givenUsers= project.getUsers();
        if(givenUsers.stream()
                .noneMatch(eachUser -> eachUser.getId().equals(user.getId())))
                throw new ProjectAccessDeniedException("Project " + projectId + " does not belong to the User " + userId);

        if (LocalDate.now().isAfter(taskDetails.getDueDate())) {
            throw new ProjectAccessDeniedException("Task's due date cannot be before the created time ");
        }
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        if(taskDetails.getStatus() != null) task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
        task.setDueDate(taskDetails.getDueDate());

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, UpdateTaskRequest taskDetails) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));


        Long userId = taskDetails.getUserId();
        Long projectId = taskDetails.getProjectId();

        User userToUse = existingTask.getUser();
        Project projectToUse = existingTask.getProject();
        LocalDate dueDateToUse = existingTask.getDueDate();
        Project project = projectId != null ? projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + projectId + " not found")) : null;

        User user = userId != null ? userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found")) : null;

        if (project != null && user != null) {
            Set<User> givenUsers= project.getUsers();
            if(givenUsers.stream()
                    .noneMatch(eachUser -> eachUser.getId().equals(user.getId())))
                throw new ProjectAccessDeniedException("Project " + projectId + " does not belong to the User " + userId);
            userToUse = user;
            projectToUse = project;

        } else if (project == null && user != null) {
            Set<User> givenUsers = existingTask.getProject().getUsers();
            if(givenUsers.stream().
                    noneMatch(eachUser -> eachUser.getId().equals(user.getId())))
                throw new ProjectAccessDeniedException("Project " + existingTask.getProject().getId() + " do not belong to the User " + userId);
            userToUse = user;

        } else if (project != null && user == null) {
            Set<User> givenUsers = project.getUsers();
            if(givenUsers.stream().
                    noneMatch(eachUser -> eachUser.getId().equals(existingTask.getUser().getId())))
                throw new ProjectAccessDeniedException("Project " + projectId + " do not belong to the User " + existingTask.getUser().getId());
            projectToUse = project;
        }

        if (taskDetails.getDueDate() != null) {
            if (existingTask.getCreatedAt().isAfter(taskDetails.getDueDate())) {
                throw new ProjectAccessDeniedException("Task's due date cannot be before the created time ");
            }
            dueDateToUse = taskDetails.getDueDate();
        }

        if (taskDetails.getTitle() != null && !taskDetails.getTitle().isBlank())
            existingTask.setTitle(taskDetails.getTitle());

        if (taskDetails.getDescription() != null && !taskDetails.getDescription().isBlank())
            existingTask.setDescription(taskDetails.getDescription());

        if (taskDetails.getStatus() != null) existingTask.setStatus(taskDetails.getStatus());

        if (taskDetails.getPriority() != null) existingTask.setPriority(taskDetails.getPriority());

        existingTask.setUser(userToUse);
        existingTask.setProject(projectToUse);
        existingTask.setDueDate(dueDateToUse);


        return taskRepository.save(existingTask);

    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    public Page<Task> getAllTasks(Task.CurrStatus status, Task.CurrPriority priority,
                                  Integer page, Integer size, String sortField, String sortDirection) {

        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
        sortField = sortField == null ? "createdAt" : sortField;
        String direction = sortDirection == null ? "asc" : sortDirection;

        if (!allowedSortFields.contains(sortField)) throw new InvalidSortFieldException("Invalid sort field: " + sortField);
        Sort sort = Sort.by(Sort.Direction.fromOptionalString(direction)
                .orElseThrow(() -> new InvalidSortDirectionException("Invalid sort direction " + direction)),
                sortField);


        PageRequest pageRequest = PageRequest.of(page, size, sort);
        if(sortField.equals("priority")) {
            if(status == null) return taskRepository.findAllTasksByPriority(pageRequest);
            else return taskRepository.findAllByStatusOrderByPriority(status, pageRequest);
        }
        if (status != null && priority == null) {
            return taskRepository.findAllByStatusOrderByPriority(status, pageRequest);
        }
        if (status == null && priority != null) {
            return taskRepository.findAllByPriority(priority, pageRequest);
        }
        if (status != null && priority != null) {
            return taskRepository.findAllByStatusAndPriority(status, priority, pageRequest);
        }
        return taskRepository.findAll(pageRequest);
    }

    public Page<Task> getTasksByProjectId(Long id, Integer page, Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
        PageRequest pageRequest = PageRequest.of(page, size);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        return taskRepository.findAllByProjectId(id, pageRequest);
    }

    public Page<Task> getTasksByUserId(Long id, Integer page, Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
        PageRequest pageRequest = PageRequest.of(page, size);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return taskRepository.findAllByUserId(id, pageRequest);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));

        taskRepository.deleteById(id);
    }
}
