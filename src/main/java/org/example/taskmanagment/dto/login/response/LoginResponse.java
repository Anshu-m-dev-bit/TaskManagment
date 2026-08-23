package org.example.taskmanagment.dto.login.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taskmanagment.dto.user.response.UserResponse;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    String message;
    String token;
    LoginUserResponse user;
}
