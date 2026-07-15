package org.example.taskmanagment.services;

import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.exceptions.ProjectNotFoundException;
import org.example.taskmanagment.exceptions.UserNotFoundException;
import org.example.taskmanagment.repositories.ProjectRepository;
import org.example.taskmanagment.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {
        Long userId = project.getUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        project.setUser(user);
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project project) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));

        Long userId = project.getUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        project.setUser(user);

        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setUser(project.getUser());

        return projectRepository.save(existingProject);
    }

    public Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));

        projectRepository.deleteById(id);
    }

}
