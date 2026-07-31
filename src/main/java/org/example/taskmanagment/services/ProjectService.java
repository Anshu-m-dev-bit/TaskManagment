package org.example.taskmanagment.services;

import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.exceptions.InvalidSortDirectionException;
import org.example.taskmanagment.exceptions.InvalidSortFieldException;
import org.example.taskmanagment.exceptions.ProjectNotFoundException;
import org.example.taskmanagment.exceptions.UserNotFoundException;
import org.example.taskmanagment.repositories.ProjectRepository;
import org.example.taskmanagment.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }
    private final static Set<String> allowedSortFields = new HashSet<>(Arrays.asList("id", "name"));
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

    public List<Project> getProjectsByUserId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        return projectRepository.findAllByUserId(id);
    }

    public Page<Project> getAllProjects(Integer page, Integer size, String sortField, String sortDirection) {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
        sortField = sortField == null ? "id" : sortField;
        String direction = sortDirection == null ? "asc" : sortDirection;

        if(!allowedSortFields.contains(sortField)) throw new InvalidSortFieldException("Invalid sort field: " + sortField);
        Sort sort = Sort.by(Sort.Direction.fromOptionalString(direction)
                .orElseThrow(() -> new InvalidSortDirectionException("Invalid sort direction " + direction)),
                sortField);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return projectRepository.findAll(pageRequest);
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));

        projectRepository.deleteById(id);
    }

}
