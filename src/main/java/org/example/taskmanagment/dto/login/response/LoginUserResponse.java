package org.example.taskmanagment.dto.login.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taskmanagment.entities.User;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserResponse {
    Long userId;
    String name;
    String email;
    User.Role role;
}
