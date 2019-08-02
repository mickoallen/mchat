package com.mick.mchat.handlers.conversation.out;

import com.mick.mchat.handlers.chat.out.ChatMessageOut;
import com.mick.mchat.handlers.conversation.ConversationType;
import com.mick.mchat.websocket.outbound.OutMessage;

import java.util.Set;
import java.util.UUID;

public class ConversationOut implements OutMessage {
    private UUID uuid;
    private ConversationType type;
    private Set<UUID> participants;
    private Set<ChatMessageOut> messages;

    public UUID getUuid() {
        return uuid;
    }

    public ConversationOut setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public ConversationType getType() {
        return type;
    }

    public ConversationOut setType(ConversationType type) {
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
}
