package com.mick.mchat;


import java.util.UUID;

/**
 * Base class for all websocket messages
 * */
public abstract class WsMessage<T> {
    private UUID userUuid;
    private WsMessageType wsMessageType;
    private long dateCreated;

    public UUID getUserUuid() {
        return userUuid;
    }

    public WsMessage<T> setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public WsMessageType getWsMessageType() {
        return wsMessageType;
    }

    public WsMessage<T> setWsMessageType(WsMessageType wsMessageType) {
        this.wsMessageType = wsMessageType;
        return this;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public WsMessage<T> setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    @Override
    public String toString() {
        return "WsMessage{" +
                "userUuid=" + userUuid +
                ", wsMessageType=" + wsMessageType +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
