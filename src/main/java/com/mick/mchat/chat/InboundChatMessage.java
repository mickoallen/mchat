package com.mick.mchat.chat;

import com.mick.mchat.websocket.inbound.InboundMessageType;
import com.mick.mchat.websocket.inbound.InboundMessage;

import java.util.UUID;


public class InboundChatMessage extends InboundMessage {
    private UUID conversationUuid;
    private ChatMessageModel messageBody;

    public InboundChatMessage() {
        super(InboundMessageType.CHAT_MESSAGE);
    }

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public InboundChatMessage setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }

    public ChatMessageModel getMessageBody() {
        return messageBody;
    }

    public InboundChatMessage setMessageBody(ChatMessageModel messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    @Override
    public String toString() {
        return "InboundChatMessage{" +
                "conversationUuid=" + conversationUuid +
                ", messageBody=" + messageBody +
                '}';
    }
}
