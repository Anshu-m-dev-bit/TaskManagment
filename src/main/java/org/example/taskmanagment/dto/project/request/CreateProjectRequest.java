package org.example.taskmanagment.dto.project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CreateProjectRequest {

    @NotBlank
    String name;

    String description;

    @NotNull
    Set<Long> userIds;
}
