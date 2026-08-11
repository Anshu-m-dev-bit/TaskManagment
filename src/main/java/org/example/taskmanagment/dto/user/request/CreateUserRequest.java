package org.example.taskmanagment.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank
    String name;

    @NotBlank
    String emailID;

    @NotBlank
    String password;
}
