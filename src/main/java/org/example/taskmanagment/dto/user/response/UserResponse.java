package org.example.taskmanagment.dto.user.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taskmanagment.entities.User;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    Long userId;
    String name;
    String emailID;
    User.Role role;
    Set<UserProjectResponse> userProjectResponses;
}
