package com.mick.mchat.websocket.outbound;

import org.eclipse.jetty.http.HttpStatus;

public abstract class OutboundMessage {
    private String message;
    private int status;

    public OutboundMessage(){
        this.status = HttpStatus.OK_200;
    }

    public OutboundMessage(int status) {
        this.status = status;
    }

    public OutboundMessage(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public OutboundMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public OutboundMessage setStatus(int status) {
        this.status = status;
        return this;
    }
}
