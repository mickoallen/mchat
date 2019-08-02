package com.mick.mchat.handlers.conversation.in;

import com.mick.mchat.websocket.inbound.InMessage;

public class ConversationGetIn implements InMessage {
    private long messageOffset;

    public long getMessageOffset() {
        return messageOffset;
    }

    public ConversationGetIn setMessageOffset(long messageOffset) {
        this.messageOffset = messageOffset;
        return this;
    }
}
