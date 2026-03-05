package ru.kosenko.greenhouse.users.exceptions;

public class IncorrectTokenException extends RuntimeException {
    public IncorrectTokenException(String message) {
        super(message);
    }
}
