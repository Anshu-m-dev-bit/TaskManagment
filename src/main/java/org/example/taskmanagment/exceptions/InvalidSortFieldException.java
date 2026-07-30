package org.example.taskmanagment.exceptions;

public class InvalidSortFieldException extends RuntimeException{
    public InvalidSortFieldException (String message) {
        super(message);
    }
}
