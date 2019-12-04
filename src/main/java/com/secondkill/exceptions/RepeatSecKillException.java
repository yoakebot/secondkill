package com.secondkill.exceptions;

public class RepeatSecKillException extends SecKillException {

    public RepeatSecKillException(String message) {
        super(message);
    }

    public RepeatSecKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
