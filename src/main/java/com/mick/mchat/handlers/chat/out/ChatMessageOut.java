package com.mick.mchat.handlers.chat.out;

import com.mick.mchat.websocket.outbound.OutMessage;

import java.util.Objects;
import java.util.UUID;

/**
 * A chat message being sent back up a websocket.
 */
public class ChatMessageOut implements OutMessage {
    private UUID userUuid;
    private UUID conversationUuid;
    private String message;
    private ChatMessageType type;
    private long dateCreated;

    public String getMessage() {
        return message;
    }

    public ChatMessageOut setMessage(String message) {
        this.message = message;
        return this;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public ChatMessageOut setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public ChatMessageOut setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public ChatMessageType getType() {
        return type;
    }

    public ChatMessageOut setType(ChatMessageType type) {
        this.type = type;
        return this;
    }

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public ChatMessageOut setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }

    @Override
    public String toString() {
        return "ChatMessageOut{" +
                "userUuid=" + userUuid +
                ", conversationUuid=" + conversationUuid +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", dateCreated=" + dateCreated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessageOut that = (ChatMessageOut) o;
        return dateCreated == that.dateCreated &&
                Objects.equals(userUuid, that.userUuid) &&
                Objects.equals(conversationUuid, that.conversationUuid) &&
                Objects.equals(message, that.message) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userUuid, conversationUuid, message, type, dateCreated);
    }
}
