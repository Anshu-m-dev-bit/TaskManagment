package org.example.taskmanagment.dto.project.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ReplaceProjectMembersRequest {
    @NotEmpty
    Set<Long> userIds;
}
