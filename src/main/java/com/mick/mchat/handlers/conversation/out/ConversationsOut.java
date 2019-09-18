package com.mick.mchat.handlers.conversation.out;

import com.mick.mchat.websocket.outbound.OutMessage;

import java.util.List;
import java.util.Objects;

/**
 * Out message for a collection of conversations
 */
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

    @Override
    public String toString() {
        return "ConversationsOut{" +
                "conversations=" + conversations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationsOut that = (ConversationsOut) o;
        return Objects.equals(conversations, that.conversations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversations);
    }
}
