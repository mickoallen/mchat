package com.mick.mchat;

public class MChatException extends Exception {
    public MChatException() {
    }

    public MChatException(String message) {
        super(message);
    }

    public MChatException(String message, Throwable cause) {
        super(message, cause);
    }

    public MChatException(Throwable cause) {
        super(cause);
    }

    public MChatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
