package com.mick.mchat.handlers.conversation.in;

import com.mick.mchat.websocket.inbound.InMessage;

import java.util.Objects;

/**
 * Request for getting conversations.
 */
public class ConversationGetIn implements InMessage {
    private long messageOffset;

    public long getMessageOffset() {
        return messageOffset;
    }

    public ConversationGetIn setMessageOffset(long messageOffset) {
        this.messageOffset = messageOffset;
        return this;
    }

    @Override
    public String toString() {
        return "ConversationGetIn{" +
                "messageOffset=" + messageOffset +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationGetIn that = (ConversationGetIn) o;
        return messageOffset == that.messageOffset;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageOffset);
    }
}
