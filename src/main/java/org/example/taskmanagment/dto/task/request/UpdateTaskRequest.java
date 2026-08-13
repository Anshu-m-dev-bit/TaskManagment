package org.example.taskmanagment.dto.task.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taskmanagment.entities.Task;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTaskRequest {
    String title;
    String description;
    LocalDate dueDate;
    Long projectId;
    Long userId;
    Task.CurrStatus status;
    Task.CurrPriority priority;
}
