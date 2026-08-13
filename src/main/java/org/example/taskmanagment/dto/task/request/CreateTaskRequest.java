package org.example.taskmanagment.dto.task.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taskmanagment.entities.Task;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CreateTaskRequest {
    @NotBlank
    String title;

    String description;

    @NotNull
    LocalDate dueDate;

    @NotNull
    Long projectId;

    @NotNull
    Long userId;

    Task.CurrStatus status;

    Task.CurrPriority priority;
}
