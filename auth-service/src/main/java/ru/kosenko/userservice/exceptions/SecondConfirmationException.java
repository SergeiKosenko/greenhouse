package ru.kosenko.userservice.exceptions;

public class SecondConfirmationException extends RuntimeException {
    public SecondConfirmationException(String message) {
        super(message);
    }
}
