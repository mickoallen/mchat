package com.mick.mchat.error;

public class MChatException extends RuntimeException {
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
