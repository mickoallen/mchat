package com.mick.mchat.handlers.chat.in;

import com.mick.mchat.websocket.inbound.InMessage;

import java.util.Objects;
import java.util.UUID;

/**
 * An incoming chat message.
 */
public class ChatMessageIn implements InMessage {
    private UUID conversationUuid;
    private String message;

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public ChatMessageIn setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
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
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessageIn that = (ChatMessageIn) o;
        return Objects.equals(conversationUuid, that.conversationUuid) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversationUuid, message);
    }
}
