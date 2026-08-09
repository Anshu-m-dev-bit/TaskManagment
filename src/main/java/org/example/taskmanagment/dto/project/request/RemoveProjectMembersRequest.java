package org.example.taskmanagment.dto.project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RemoveProjectMembersRequest {
    @NotEmpty
    Set<Long> userIds;
}
