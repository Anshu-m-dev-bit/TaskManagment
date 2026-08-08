package org.example.taskmanagment.dto.project.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProjectRequest {
    String name;
    String description;
}
