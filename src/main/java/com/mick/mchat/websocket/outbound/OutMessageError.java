package com.mick.mchat.websocket.outbound;

public class OutMessageError implements OutMessage {
    private String message;
    private int status;

    public String getMessage() {
        return message;
    }

    public OutMessageError setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public OutMessageError setStatus(int status) {
        this.status = status;
        return this;
    }
}
