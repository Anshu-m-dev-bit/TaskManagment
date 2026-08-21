package org.example.taskmanagment.dto.login.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taskmanagment.entities.User;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    String email;

    @NotBlank
    String password;

}
