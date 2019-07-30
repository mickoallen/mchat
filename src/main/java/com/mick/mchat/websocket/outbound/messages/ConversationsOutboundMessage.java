package com.mick.mchat.websocket.outbound.messages;

import com.mick.mchat.conversation.Conversation;
import com.mick.mchat.websocket.outbound.OutboundMessage;

import java.util.List;

public class ConversationsOutboundMessage extends OutboundMessage {
    private List<Conversation> conversations;

    public List<Conversation> getConversations() {
        return conversations;
    }

    public ConversationsOutboundMessage setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
        return this;
    }
}
