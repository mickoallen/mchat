package com.mick.mchat.handlers.conversation.out;

import com.mick.mchat.handlers.conversation.Conversation;
import com.mick.mchat.websocket.outbound.OutMessage;

import java.util.List;

public class ConversationsOut implements OutMessage {
    private List<Conversation> conversations;

    public ConversationsOut() {
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public ConversationsOut setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
        return this;
    }
}
