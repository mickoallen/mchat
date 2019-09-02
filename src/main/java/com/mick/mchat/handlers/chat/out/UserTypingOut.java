package com.mick.mchat.handlers.chat.out;

import com.mick.mchat.websocket.outbound.OutMessage;

import java.util.UUID;

public class UserTypingOut implements OutMessage {
    private UUID userUuid;
    private UUID conversationUuid;

    public UUID getUserUuid() {
        return userUuid;
    }

    public UserTypingOut setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public UserTypingOut setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }
}
