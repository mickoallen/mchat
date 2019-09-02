package com.mick.mchat.handlers.chat.in;

import com.mick.mchat.websocket.inbound.InMessage;

import java.util.UUID;

public class UserTypingIn implements InMessage {
    private UUID conversationUuid;

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public UserTypingIn setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }
}
