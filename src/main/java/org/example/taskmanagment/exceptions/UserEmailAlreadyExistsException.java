package org.example.taskmanagment.exceptions;

public class UserEmailAlreadyExistsException extends RuntimeException{
    public UserEmailAlreadyExistsException(String message) {
        super(message);
    }
}
