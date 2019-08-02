package com.mick.mchat.handlers.conversation.in;

import com.mick.mchat.websocket.inbound.InMessage;

import java.util.List;
import java.util.UUID;

public class AddUsersToConversationIn implements InMessage {
    private UUID conversationUuid;
    private List<UUID> users;
}
