package org.example.taskmanagment.exceptions;

public class UserAccessDeniedException extends RuntimeException{
    public UserAccessDeniedException(String message) {
        super(message);
    }
}
