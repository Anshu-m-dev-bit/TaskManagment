package org.example.taskmanagment.exceptions;

public class ProjectAccessDeniedException extends RuntimeException{
    public ProjectAccessDeniedException(String message) {
        super(message);
    }
}
