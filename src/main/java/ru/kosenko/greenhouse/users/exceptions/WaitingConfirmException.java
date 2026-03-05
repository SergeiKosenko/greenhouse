package ru.kosenko.greenhouse.users.exceptions;

public class WaitingConfirmException extends RuntimeException {
    public WaitingConfirmException(String message) {
        super(message);
    }
}
