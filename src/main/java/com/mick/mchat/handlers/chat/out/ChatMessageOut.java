package com.mick.mchat.handlers.chat.out;

import com.mick.mchat.websocket.outbound.OutMessage;

public class ChatMessageOut implements OutMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public ChatMessageOut setMessage(String message) {
        this.message = message;
        return this;
    }
}
