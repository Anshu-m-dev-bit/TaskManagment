package org.example.taskmanagment.dto.task.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskUserResponse {
    Long userId;
    String name;
    String emailId;
}
