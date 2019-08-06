package com.mick.mchat.handlers.conversation.in;

import com.mick.mchat.websocket.inbound.InMessage;

import java.util.List;
import java.util.UUID;

public class CreateConversationIn implements InMessage {
    private List<UUID> users;

    public List<UUID> getUsers() {
        return users;
    }

    public CreateConversationIn setUsers(List<UUID> users) {
        this.users = users;
        return this;
    }
}
