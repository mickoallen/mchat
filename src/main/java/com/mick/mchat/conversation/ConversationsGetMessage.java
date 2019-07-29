package com.mick.mchat.conversation;

import com.mick.mchat.websocket.model.MessageBody;
import com.mick.mchat.websocket.model.MessageType;

public class ConversationsGetMessage extends MessageBody {
    public ConversationsGetMessage() {
        super(MessageType.CONVERSATIONS_GET);
    }
}
