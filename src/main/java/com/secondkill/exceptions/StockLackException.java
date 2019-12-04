package com.secondkill.exceptions;

public class StockLackException extends SecKillException {
    public StockLackException(String message) {
        super(message);
    }

    public StockLackException(String message, Throwable cause) {
        super(message, cause);
    }
}
