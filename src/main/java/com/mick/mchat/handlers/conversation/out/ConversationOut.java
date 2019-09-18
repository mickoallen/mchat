package com.mick.mchat.handlers.conversation.out;

import com.mick.mchat.handlers.chat.out.ChatMessageOut;
import com.mick.mchat.websocket.outbound.OutMessage;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Conversation out message.
 */
public class ConversationOut implements OutMessage {
    private UUID uuid;
    private String name;
    private String type;
    private Set<UUID> participants;
    private Set<ChatMessageOut> messages;

    public UUID getUuid() {
        return uuid;
    }

    public ConversationOut setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getType() {
        return type;
    }

    public ConversationOut setType(String type) {
        this.type = type;
        return this;
    }

    public Set<UUID> getParticipants() {
        return participants;
    }

    public ConversationOut setParticipants(Set<UUID> participants) {
        this.participants = participants;
        return this;
    }

    public Set<ChatMessageOut> getMessages() {
        return messages;
    }

    public ConversationOut setMessages(Set<ChatMessageOut> messages) {
        this.messages = messages;
        return this;
    }

    public String getName() {
        return name;
    }

    public ConversationOut setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "ConversationOut{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", participants=" + participants +
                ", messages=" + messages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationOut that = (ConversationOut) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(participants, that.participants) &&
                Objects.equals(messages, that.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, type, participants, messages);
    }
}
