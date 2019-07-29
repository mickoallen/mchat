package com.mick.mchat.error;

public class MChatRuntimeException extends RuntimeException {
    public MChatRuntimeException() {
    }

    public MChatRuntimeException(String message) {
        super(message);
    }

    public MChatRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MChatRuntimeException(Throwable cause) {
        super(cause);
    }

    public MChatRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
