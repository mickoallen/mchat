package com.mick.mchat;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mick.mchat.conversation.WsChatMessage;
import com.mick.mchat.user.WsUserConnectedMessage;

import java.util.UUID;

/**
 * Base class for all websocket messages, lets do some polymorphic deserialization yehawww
 * */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "wsMessageType",
        defaultImpl = WsChatMessage.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WsChatMessage.class, name = "CHAT_MESSAGE"),
        @JsonSubTypes.Type(value = WsUserConnectedMessage.class, name = "USER_CONNECTED"),
})
public abstract class WsMessage<T> {
    private UUID userUuid;
    private WsMessageType wsMessageType;
    private long dateCreated;
    private T body;

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

    public T getBody() {
        return body;
    }

    public WsMessage<T> setBody(T body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        return "WsMessage{" +
                "userUuid=" + userUuid +
                ", wsMessageType=" + wsMessageType +
                ", dateCreated=" + dateCreated +
                ", body=" + body +
                '}';
    }
}
