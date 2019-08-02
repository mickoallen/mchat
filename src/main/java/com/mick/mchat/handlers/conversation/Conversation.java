package com.mick.mchat.handlers.conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Conversation {
    private UUID uuid;
    private List<UUID> participants = new ArrayList<>();
    private ConversationType conversationType;

    public UUID getUuid() {
        return uuid;
    }

    public Conversation setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public List<UUID> getParticipants() {
        return participants;
    }

    public Conversation setParticipants(List<UUID> participants) {
        this.participants = participants;
        return this;
    }

    public ConversationType getConversationType() {
        return conversationType;
    }

    public Conversation setConversationType(ConversationType conversationType) {
        this.conversationType = conversationType;
        return this;
    }
}
