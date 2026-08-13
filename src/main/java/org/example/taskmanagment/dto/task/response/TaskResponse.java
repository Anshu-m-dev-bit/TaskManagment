package org.example.taskmanagment.dto.task.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taskmanagment.entities.Task;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponse {
    Long taskId;
    String title;
    String description;
    Task.CurrStatus status;
    Task.CurrPriority priority;
    LocalDate dueDate;
    LocalDate createdAt;
    TaskUserResponse user;
    TaskProjectResponse project;
}
