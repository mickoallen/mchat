package com.mick.mchat.conversation;

public class ChatMessageApiModel {
    private long dateCreated;
    private String body;
    private ChatMessageType type;

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
}
