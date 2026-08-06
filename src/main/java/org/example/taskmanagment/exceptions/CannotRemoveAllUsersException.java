package org.example.taskmanagment.exceptions;

public class CannotRemoveAllUsersException extends RuntimeException{
    public CannotRemoveAllUsersException(String message) {
        super(message);
    }
}
