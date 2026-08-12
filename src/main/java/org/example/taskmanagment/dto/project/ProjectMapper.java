package org.example.taskmanagment.dto.project;

import org.example.taskmanagment.dto.project.response.ProjectResponse;
import org.example.taskmanagment.dto.project.response.ProjectUserResponse;
import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ProjectMapper {
    public ProjectResponse toProjectResponse(Project project) {
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setProjectId(project.getId());
        projectResponse.setName(project.getName());
        projectResponse.setDescription(project.getDescription());
        projectResponse.setProjectUserResponse(toProjectUsersResponse(project.getUsers()));
        return projectResponse;
    }
    public Set<ProjectUserResponse> toProjectUsersResponse(Set<User> users) {
        Set<ProjectUserResponse> projectUserResponses = new HashSet<>();
        for (User user: users) {
            ProjectUserResponse userResponse = new ProjectUserResponse();
            userResponse.setUserId(user.getId());
            userResponse.setName(user.getName());
            projectUserResponses.add(userResponse);
        }
        return projectUserResponses;
    }
}
