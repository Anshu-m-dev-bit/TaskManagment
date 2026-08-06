package org.example.taskmanagment.services;

import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.exceptions.*;
import org.example.taskmanagment.repositories.ProjectRepository;
import org.example.taskmanagment.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

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
         Set<User> givenUsers= project.getUsers();
         Set<User> validatedUsers = new HashSet<>();
         Set<Long> invalidUserIds = new HashSet<>();
         for (User user: givenUsers) {
             Long userId = user.getId();
             Optional<User> extractedUser = userRepository.findById(userId);
             if (extractedUser.isPresent()) {
                 User presentUser = extractedUser.get();
                 validatedUsers.add(presentUser);
             }
             else {
                 invalidUserIds.add(userId);
             }
         }
         if(!invalidUserIds.isEmpty()) throw new UserNotFoundException("Users with id(s) " + invalidUserIds + "does not exists");
         project.setUsers(validatedUsers);

         for (User user: validatedUsers) {
             user.addProject(project);
         }

         return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project project) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));

        Set<User> givenUsers= project.getUsers();
        Set<User> validatedUsers = new HashSet<>();
        Set<Long> invalidUserIds = new HashSet<>();
         for (User user: givenUsers) {
             Long userId = user.getId();
             Optional<User> extractedUser = userRepository.findById(userId);
             if (extractedUser.isPresent()) {
                 User presentUser = extractedUser.get();
                 validatedUsers.add(presentUser);
             }
             else {
                 invalidUserIds.add(userId);
             }
         }

         if(!invalidUserIds.isEmpty()) throw new UserNotFoundException("Users with id(s) " + invalidUserIds + "does not exists");
         project.setUsers(validatedUsers);

         for (User user: validatedUsers) {
            user.addProject(existingProject);
         }

         existingProject.setName(project.getName());
         existingProject.setDescription(project.getDescription());
         existingProject.setUsers(project.getUsers());

         return projectRepository.save(existingProject);
    }

    public Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
    }

    public List<Project> getProjectsByUserId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        return projectRepository.findAllByUsersId(id);
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

    public Project replaceProjectMembers(Long id, Set<User> givenUsers) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));

        Set<User> availableUsers = new HashSet<>(project.getUsers());
        Set<User> validatedUsers = new HashSet<>();
        Set<Long> invalidUserIds = new HashSet<>();
        for (User user: givenUsers) {
            Long userId = user.getId();
            Optional<User> extractedUser = userRepository.findById(userId);
            if (extractedUser.isPresent()) {
                User presentUser = extractedUser.get();
                validatedUsers.add(presentUser);
            }
            else {
                invalidUserIds.add(userId);
            }
        }

        if(!invalidUserIds.isEmpty()) throw new UserNotFoundException("Users with id(s) " + invalidUserIds + " do not exists");

        for (User user: availableUsers) {
            user.removeProject(project);
        }

        for (User user: validatedUsers) {
            user.addProject(project);
        }

        return projectRepository.save(project);
    }

    public Project addProjectMembers(Long id, Set<User> givenUsers) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));

        Set<User> validatedUsers = new HashSet<>();
        Set<Long> invalidUserIds = new HashSet<>();
        for (User user: givenUsers) {
            Long userId = user.getId();
            Optional<User> extractedUser = userRepository.findById(userId);
            if (extractedUser.isPresent()) {
                User presentUser = extractedUser.get();
                validatedUsers.add(presentUser);
            }
            else {
                invalidUserIds.add(userId);
            }
        }

        if(!invalidUserIds.isEmpty()) throw new UserNotFoundException("Users with id(s) " + invalidUserIds + "does not exists");

        for (User user: validatedUsers) {
            user.addProject(project);
        }

        return projectRepository.save(project);
    }

    public Project removeProjectMembers(Long id, Set<User> givenUsers) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));

        Set<User> availableUsers = project.getUsers();
        Set<User> validatedUsers = new HashSet<>();
        Set<Long> absentUser = new HashSet<>();
        Set<Long> invalidUserIds = new HashSet<>();

        for (User user: givenUsers) {
            Long userId = user.getId();
            Optional<User> extractedUser = userRepository.findById(userId);
            if (extractedUser.isPresent() && availableUsers.contains(extractedUser.get())) {
                validatedUsers.add(extractedUser.get());
            }
            else if (extractedUser.isPresent()) {
                absentUser.add(userId);
            }
            else {
                invalidUserIds.add(userId);
            }
        }

        if(!invalidUserIds.isEmpty()) throw new UserNotFoundException("Users with id(s) " + invalidUserIds + "do not exists");
        if(!absentUser.isEmpty()) throw new UserAccessDeniedException("Users with id(s) " + absentUser + " do not belong to this project");

        Integer currentSize = availableUsers.size();
        for (User user: validatedUsers) {
            if (currentSize > 1) {
                user.removeProject(project);
                currentSize--;
            }
            else {
                throw new CannotRemoveAllUsersException("All users can't be removed from the project");
            }
        }
        return projectRepository.save(project);

    }
}
