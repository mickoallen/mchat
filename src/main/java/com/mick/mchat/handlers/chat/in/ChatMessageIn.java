package com.mick.mchat.handlers.chat.in;

import com.mick.mchat.handlers.chat.ChatMessage;
import com.mick.mchat.websocket.inbound.InMessage;

import java.util.UUID;


public class ChatMessageIn implements InMessage {
    private UUID conversationUuid;
    private ChatMessage messageBody;
    private String message;

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public ChatMessageIn setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }

    public ChatMessage getMessageBody() {
        return messageBody;
    }

    public ChatMessageIn setMessageBody(ChatMessage messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ChatMessageIn setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "ChatMessageIn{" +
                "conversationUuid=" + conversationUuid +
                ", messageBody=" + messageBody +
                '}';
    }
}
