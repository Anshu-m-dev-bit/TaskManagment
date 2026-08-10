package org.example.taskmanagment.dto.project.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProjectResponse {
    Long projectId;
    String name;
    String description;
    Set<ProjectUserResponse> projectUserResponse;
}

