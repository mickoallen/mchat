package com.mick.mchat.websocket;

import com.mick.mchat.WsMessage;
import com.mick.mchat.user.User;

public class MessageDispatcher {

    public void dispatchMessage(User user, WsMessage wsMessage){
        switch (wsMessage.getWsMessageType()) {
            case CHAT_MESSAGE:
                break;
            case USER_CONNECTED:
                break;
            case USER_DISCONNECTED:
                break;
            case NOTIFICATION:
                break;
        }
    }
}
