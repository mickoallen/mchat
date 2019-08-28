package com.mick.mchat.handlers.chat.out;

import com.mick.mchat.websocket.outbound.OutMessage;

import java.util.UUID;

public class ChatMessageOut implements OutMessage {
    private UUID userUuid;
    private UUID conversationUuid;
    private String message;
    private String type;
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

    public String getType() {
        return type;
    }

    public ChatMessageOut setType(String type) {
        this.type = type;
        return this;
    }

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public ChatMessageOut setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }
}
