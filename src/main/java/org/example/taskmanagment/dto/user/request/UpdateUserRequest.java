package org.example.taskmanagment.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taskmanagment.entities.User;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest {
    String name;
    String emailID;
    String password;
    User.Role role;
}
