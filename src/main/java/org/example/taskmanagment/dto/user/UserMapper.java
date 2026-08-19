package org.example.taskmanagment.dto.user;

import org.example.taskmanagment.dto.project.ProjectMapper;
import org.example.taskmanagment.dto.user.response.UserProjectResponse;
import org.example.taskmanagment.dto.user.response.UserResponse;
import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {
    public UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmailID(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setUserProjectResponses(toUserProjectsResponse(user.getProjects()));
        return userResponse;
    }
    public Set<UserProjectResponse> toUserProjectsResponse(Set<Project> projects) {
        Set<UserProjectResponse> userProjectResponses = new HashSet<>();
        for (Project project: projects) {
            UserProjectResponse userProjectResponse = new UserProjectResponse();
            userProjectResponse.setProjectId(project.getId());
            userProjectResponse.setProjectName(project.getName());
            userProjectResponses.add(userProjectResponse);
        }
        return userProjectResponses;
    }
}
