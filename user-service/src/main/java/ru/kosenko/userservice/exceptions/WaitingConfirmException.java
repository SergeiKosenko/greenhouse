package ru.kosenko.userservice.exceptions;

public class WaitingConfirmException extends RuntimeException {
    public WaitingConfirmException(String message) {
        super(message);
    }
}
