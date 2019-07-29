package com.mick.mchat.websocket.model;

import java.util.UUID;

public class MessageContext {
    private UUID userUuid;
    private long dateCreated;

    public UUID getUserUuid() {
        return userUuid;
    }

    public MessageContext setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public MessageContext setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    @Override
    public String toString() {
        return "MessageContext{" +
                "userUuid=" + userUuid +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
