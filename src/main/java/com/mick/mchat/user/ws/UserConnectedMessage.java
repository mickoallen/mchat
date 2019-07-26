package com.mick.mchat.user.ws;

import com.mick.mchat.websocket.WebsocketMessage;
import com.mick.mchat.websocket.WebsocketMessageType;

public class UserConnectedMessage extends WebsocketMessage {
    public UserConnectedMessage(){
        setWebsocketMessageType(WebsocketMessageType.USER_CONNECTED);
    }
}
