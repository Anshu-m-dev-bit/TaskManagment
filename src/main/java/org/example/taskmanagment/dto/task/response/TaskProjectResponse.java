package org.example.taskmanagment.dto.task.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskProjectResponse {
    Long projectId;
    String name;
    String description;
}
