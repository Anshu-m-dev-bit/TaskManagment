package org.example.taskmanagment.dto.login;

import org.example.taskmanagment.dto.login.request.LoginRequest;
import org.example.taskmanagment.dto.login.response.LoginResponse;
import org.example.taskmanagment.dto.login.response.LoginUserResponse;
import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {
    private final UserService userService;

    public LoginMapper(UserService userService) {
        this.userService = userService;
    }
    public LoginResponse toLoginResponse(LoginRequest loginDetails, String message, String token) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage(message);
        loginResponse.setToken(token);

        LoginUserResponse loginUserResponse = toLoginUserResponse(userService
                .getUserByEmail(loginDetails.getEmail()));

        loginResponse.setUser(loginUserResponse);

        return loginResponse;

    }

    public LoginUserResponse toLoginUserResponse(User user) {
        LoginUserResponse loginUserResponse = new LoginUserResponse();
        loginUserResponse.setUserId(user.getId());
        loginUserResponse.setEmail(user.getEmail());
        loginUserResponse.setRole(user.getRole());
        return loginUserResponse;
    }


}
