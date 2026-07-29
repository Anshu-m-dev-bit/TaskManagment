package org.example.taskmanagment.services;

import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.Task;
import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.exceptions.ProjectAccessDeniedException;
import org.example.taskmanagment.exceptions.ProjectNotFoundException;
import org.example.taskmanagment.exceptions.TaskNotFoundException;
import org.example.taskmanagment.exceptions.UserNotFoundException;
import org.example.taskmanagment.repositories.ProjectRepository;
import org.example.taskmanagment.repositories.TaskRepository;
import org.example.taskmanagment.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public Task createTask(Task task) {
        Long userId = task.getUser().getId();
        Long projectId = task.getProject().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        task.setUser(user);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + projectId + " not found"));
        task.setProject(project);

        if(!Objects.equals(project.getUser().getId(), userId)) {
            throw new ProjectAccessDeniedException("Project " + projectId + " does not belong to the User " + userId );
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));

        Long userId = task.getUser().getId();
        Long projectId = task.getProject().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        task.setUser(user);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + projectId + " not found"));
        task.setProject(project);

        if(!Objects.equals(project.getUser().getId(), userId)) {
            throw new ProjectAccessDeniedException("Project " + projectId + " does not belong to the User " + userId );
        }

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setProject(task.getProject());
        existingTask.setUser(task.getUser());
        return taskRepository.save(existingTask);

    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    public Page<Task> getAllTasks(Task.CurrStatus status, Task.CurrPriority priority,
                                  Integer page, Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
        PageRequest pageRequest = PageRequest.of(page, size);
        if(status != null && priority == null) {
            return taskRepository.findAllByStatus(status, pageRequest);
        }
        if (status == null && priority != null) {
            return taskRepository.findAllByPriority(priority, pageRequest);
        }
        if(status != null && priority != null) {
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
