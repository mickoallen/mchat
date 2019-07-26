package com.mick.mchat.conversation;

import java.util.UUID;

public class ChatMessageApiModel {
    private long dateCreated;
    private String body;
    private ChatMessageType type;
    private UUID conversationUuid;

    public long getDateCreated() {
        return dateCreated;
    }

    public ChatMessageApiModel setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public String getBody() {
        return body;
    }

    public ChatMessageApiModel setBody(String body) {
        this.body = body;
        return this;
    }

    public ChatMessageType getType() {
        return type;
    }

    public ChatMessageApiModel setType(ChatMessageType type) {
        this.type = type;
        return this;
    }

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public ChatMessageApiModel setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }
}
