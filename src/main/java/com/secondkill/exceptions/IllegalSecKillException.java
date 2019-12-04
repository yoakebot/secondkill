package com.secondkill.exceptions;

public class IllegalSecKillException extends SecKillException {
    public IllegalSecKillException(String message) {
        super(message);
    }

    public IllegalSecKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
