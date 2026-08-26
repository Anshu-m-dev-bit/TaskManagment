package org.example.taskmanagment.services;

import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.exceptions.UserAuthorisationException;
import org.example.taskmanagment.repositories.ProjectRepository;
import org.example.taskmanagment.repositories.TaskRepository;
import org.example.taskmanagment.repositories.UserRepository;
import org.example.taskmanagment.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthorizationService {
    private final UserRepository userRepository;
    private final CustomUserDetails customUserDetails;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public Long getAssignableUserId(Long assignedUserId) {
        User loggedInUser = customUserDetails.getUser();

        User.Role assignedUserRole = userRepository.findById(assignedUserId).get().getRole();

        if (loggedInUser.getRole() == User.Role.USER ||
                loggedInUser.getId().equals(assignedUserId)) {
            return loggedInUser.getId();
        } else if (( loggedInUser.getRole() == User.Role.MANAGER && assignedUserRole == User.Role.USER ) ||
                ( loggedInUser.getRole() == User.Role.ADMIN && assignedUserRole != User.Role.ADMIN )) {
            return assignedUserId;
        } else {
            throw new UserAuthorisationException("User not allowed to perform this action");
        }
    }

    public Set<User> validateProjectAssignment(Set<User> assignedUserIds) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User loggedInUser = customUserDetails.getUser();
        Set<User> assignedUsers = new HashSet<>();
        assignedUsers.add(loggedInUser);


        if (loggedInUser.getRole() == User.Role.MANAGER) {
            for (User user: assignedUserIds) {
                if (user.getRole() != User.Role.USER && !loggedInUser.getId().equals(user.getId())) {
                    throw new UserAuthorisationException("User is not authorized to perform this action");
                }
                assignedUsers.add(user);
            }
        } else if (loggedInUser.getRole() == User.Role.ADMIN) {
            for (User user: assignedUserIds) {
                if (user.getRole() == User.Role.ADMIN && !loggedInUser.getId().equals(user.getId())) {
                    throw new UserAuthorisationException("User is not authorized to perform this action");
                }
                assignedUsers.add(user);
            }
        } else {
            throw new UserAuthorisationException("User is not authorized to perform this action");
        }

        return assignedUsers;
    }

    public boolean isUserDeleteTaskRequestValid() {
        return false;
    }
    public boolean isUserDeleteProjectRequestValid() {
        return false;
    }

}
