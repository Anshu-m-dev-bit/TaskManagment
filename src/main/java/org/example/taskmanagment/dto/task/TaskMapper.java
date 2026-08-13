package org.example.taskmanagment.dto.task;

import org.example.taskmanagment.dto.task.response.TaskProjectResponse;
import org.example.taskmanagment.dto.task.response.TaskResponse;
import org.example.taskmanagment.dto.task.response.TaskUserResponse;
import org.example.taskmanagment.entities.Project;
import org.example.taskmanagment.entities.Task;
import org.example.taskmanagment.entities.User;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskResponse toTaskResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTaskId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setStatus(task.getStatus());
        taskResponse.setPriority(task.getPriority());
        taskResponse.setDueDate(task.getDueDate());
        taskResponse.setCreatedAt(task.getCreatedAt());

        taskResponse.setUser(toTaskUserResponse(task.getUser()));
        taskResponse.setProject(toTaskProjectResponse(task.getProject()));
        return taskResponse;
    }
    public TaskProjectResponse toTaskProjectResponse(Project project) {
        TaskProjectResponse taskProjectResponse = new TaskProjectResponse();
        taskProjectResponse.setProjectId(project.getId());
        taskProjectResponse.setDescription(project.getDescription());
        return taskProjectResponse;
    }

    public TaskUserResponse toTaskUserResponse(User user) {
        TaskUserResponse taskUserResponse = new TaskUserResponse();
        taskUserResponse.setUserId(user.getId());
        taskUserResponse.setName(user.getName());
        taskUserResponse.setEmailId(user.getEmail());
        return taskUserResponse;
    }
}
