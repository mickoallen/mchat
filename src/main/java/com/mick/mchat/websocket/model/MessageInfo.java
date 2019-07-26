package com.mick.mchat.websocket.model;

import java.util.UUID;

public class MessageInfo {
    private UUID userUuid;
    private long dateCreated;

    public UUID getUserUuid() {
        return userUuid;
    }

    public MessageInfo setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public MessageInfo setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "userUuid=" + userUuid +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
