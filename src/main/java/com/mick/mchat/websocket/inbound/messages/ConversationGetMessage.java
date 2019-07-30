package com.mick.mchat.websocket.inbound.messages;

import com.mick.mchat.websocket.inbound.InboundMessage;
import com.mick.mchat.websocket.inbound.InboundMessageType;

import java.util.Optional;
import java.util.UUID;

public class ConversationGetMessage extends InboundMessage {
    private UUID conversationUuid;
    private Long messagesAfterTime;
    private int messageLimit;

    public ConversationGetMessage() {
        super(InboundMessageType.CONVERSATION_GET);
        messageLimit = 1000;//default
    }

    public Optional<UUID> getConversationUuid() {
        return Optional.ofNullable(conversationUuid);
    }

    public ConversationGetMessage setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }

    public Optional<Long> getMessagesAfterTime() {
        return Optional.ofNullable(messagesAfterTime);
    }

    public ConversationGetMessage setMessagesAfterTime(long messagesAfterTime) {
        this.messagesAfterTime = messagesAfterTime;
        return this;
    }

    public int getMessageLimit() {
        return messageLimit;
    }

    public ConversationGetMessage setMessageLimit(int messageLimit) {
        this.messageLimit = messageLimit;
        return this;
    }
}
