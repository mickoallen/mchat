package com.mick.mchat.conversation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Conversation {
    private UUID uuid;
    private List<UUID> participants = new ArrayList<>();

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
}
