package com.mick.mchat.websocket.inbound.messages;

import com.mick.mchat.websocket.inbound.InboundMessage;
import com.mick.mchat.websocket.inbound.InboundMessageType;

public class UserConnectedMessage extends InboundMessage {
    public UserConnectedMessage() {
        super(InboundMessageType.USER_CONNECTED);
    }
}
