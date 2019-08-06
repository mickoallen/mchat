package com.mick.mchat.handlers.chat.out;

import com.mick.mchat.jooq.model.enums.MessageType;
import com.mick.mchat.websocket.outbound.OutMessage;

import java.util.UUID;

public class ChatMessageOut implements OutMessage {
    private UUID userUuid;
    private String message;
    private MessageType type;
    private long dateCreated;

    public String getMessage() {
        return message;
    }

    public ChatMessageOut setMessage(String message) {
        this.message = message;
        return this;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public ChatMessageOut setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public ChatMessageOut setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public MessageType getType() {
        return type;
    }

    public ChatMessageOut setType(MessageType type) {
        this.type = type;
        return this;
    }
}
