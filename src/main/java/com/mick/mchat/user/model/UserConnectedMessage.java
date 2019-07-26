package com.mick.mchat.user.model;

import com.mick.mchat.websocket.model.MessageBody;
import com.mick.mchat.websocket.model.MessageType;

public class UserConnectedMessage extends MessageBody {
    public UserConnectedMessage() {
        super(MessageType.USER_CONNECTED);
    }
}
