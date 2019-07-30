package com.mick.mchat.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mick.mchat.chat.InboundChatMessage;
import com.mick.mchat.websocket.inbound.messages.ConversationGetMessage;
import com.mick.mchat.websocket.inbound.messages.UserLoginMessage;

import java.time.Instant;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        defaultImpl = NoMappingInboundMessage.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InboundChatMessage.class, name = InboundMessageType.CHAT_MESSAGE_VALUE),
        @JsonSubTypes.Type(value = UserLoginMessage.class, name = InboundMessageType.USER_LOGIN_VALUE),
        @JsonSubTypes.Type(value = ConversationGetMessage.class, name = InboundMessageType.CONVERSATION_GET_VALUE),
})
public abstract class InboundMessage {
    private final InboundMessageType type;
    private UUID userUuid;
    private long dateReceived;

    protected InboundMessage(InboundMessageType type) {
        this.type = type;
        dateReceived = Instant.now().toEpochMilli();
    }

    public InboundMessageType getType() {
        return type;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public InboundMessage setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public long getDateReceived() {
        return dateReceived;
    }

    public InboundMessage setDateReceived(long dateReceived) {
        this.dateReceived = dateReceived;
        return this;
    }
}
