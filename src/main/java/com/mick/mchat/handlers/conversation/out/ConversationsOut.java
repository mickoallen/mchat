package com.mick.mchat.handlers.conversation.out;

import com.mick.mchat.websocket.outbound.OutMessage;

import java.util.List;

public class ConversationsOut implements OutMessage {
    private List<ConversationOut> conversations;

    public ConversationsOut() {
    }

    public List<ConversationOut> getConversations() {
        return conversations;
    }

    public ConversationsOut setConversations(List<ConversationOut> conversations) {
        this.conversations = conversations;
        return this;
    }
}
