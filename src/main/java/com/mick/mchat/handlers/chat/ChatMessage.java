package com.mick.mchat.handlers.chat;

import java.util.UUID;

public class ChatMessage {
    private long dateCreated;
    private String body;
    private ChatMessageType type;
    private UUID conversationUuid;

    public long getDateCreated() {
        return dateCreated;
    }

    public ChatMessage setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public String getBody() {
        return body;
    }

    public ChatMessage setBody(String body) {
        this.body = body;
        return this;
    }

    public ChatMessageType getType() {
        return type;
    }

    public ChatMessage setType(ChatMessageType type) {
        this.type = type;
        return this;
    }

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public ChatMessage setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }
}
