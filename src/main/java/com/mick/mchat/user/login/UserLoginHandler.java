package com.mick.mchat.user.login;

import com.mick.mchat.error.MChatException;
import com.mick.mchat.user.UserService;
import com.mick.mchat.websocket.MessageHandler;
import com.mick.mchat.websocket.model.MessageType;
import com.mick.mchat.websocket.model.WebsocketMessage;
import io.javalin.websocket.WsContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserLoginHandler implements MessageHandler<UserLoginMessageBody> {
    private UserService userService;

    @Inject
    public UserLoginHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleMessage(WebsocketMessage<UserLoginMessageBody> websocketMessage, WsContext wsContext) throws MChatException {

    }

    @Override
    public MessageType getMessageType() {
        return MessageType.USER_LOGIN;
    }
}
