package ru.kosenko.userservice.exceptions;

public class IncorrectTokenException extends RuntimeException {
    public IncorrectTokenException(String message) {
        super(message);
    }
}
