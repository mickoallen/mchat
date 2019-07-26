package com.mick.mchat.chat;

import java.util.UUID;

public class ChatMessageModel {
    private long dateCreated;
    private String body;
    private ChatMessageType type;
    private UUID conversationUuid;

    public long getDateCreated() {
        return dateCreated;
    }

    public ChatMessageModel setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public String getBody() {
        return body;
    }

    public ChatMessageModel setBody(String body) {
        this.body = body;
        return this;
    }

    public ChatMessageType getType() {
        return type;
    }

    public ChatMessageModel setType(ChatMessageType type) {
        this.type = type;
        return this;
    }

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public ChatMessageModel setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }
}
