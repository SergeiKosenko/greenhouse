package ru.kosenko.greenhouse.users.exceptions;

public class SecondConfirmationException extends RuntimeException {
    public SecondConfirmationException(String message) {
        super(message);
    }
}
