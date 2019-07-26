package com.mick.mchat.websocket;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mick.mchat.conversation.ws.WebsocketChatMessage;
import com.mick.mchat.user.ws.WebsocketUserConnectedMessage;

import java.util.UUID;

/**
 * Base class for all websocket messages, lets do some polymorphic deserialization yehawww
 * */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "websocketMessageType",
        defaultImpl = WebsocketChatMessage.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WebsocketChatMessage.class, name = "CHAT_MESSAGE"),
        @JsonSubTypes.Type(value = WebsocketUserConnectedMessage.class, name = "USER_CONNECTED"),
})
public abstract class WebsocketMessage {
    private UUID userUuid;
    private WebsocketMessageType websocketMessageType;
    private long dateCreated;

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public WebsocketMessageType getWebsocketMessageType() {
        return websocketMessageType;
    }

    public void setWebsocketMessageType(WebsocketMessageType websocketMessageType) {
        this.websocketMessageType = websocketMessageType;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "WebsocketMessage{" +
                "userUuid=" + userUuid +
                ", websocketMessageType=" + websocketMessageType +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
