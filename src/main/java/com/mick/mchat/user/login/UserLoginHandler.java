package com.mick.mchat.user.login;

import com.mick.mchat.error.MChatException;
import com.mick.mchat.user.security.AddCookieMessage;
import com.mick.mchat.user.security.AuthenticationService;
import com.mick.mchat.websocket.MessageHandler;
import com.mick.mchat.websocket.WebsocketMessageMapper;
import com.mick.mchat.websocket.model.MessageType;
import com.mick.mchat.websocket.model.WebsocketMessage;
import io.javalin.websocket.WsContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserLoginHandler implements MessageHandler<UserLoginMessageBody> {
    private AuthenticationService authenticationService;

    @Inject
    public UserLoginHandler(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void handleMessage(WebsocketMessage<UserLoginMessageBody> websocketMessage, WsContext wsContext) throws MChatException {
        String authToken = authenticationService.login(websocketMessage.getBody().getUsername(), websocketMessage.getBody().getPassword());

        wsContext.send(
                WebsocketMessageMapper.serialize(
                        new WebsocketMessage<AddCookieMessage>()
                                .setBody(
                                        new AddCookieMessage()
                                                .setName(AuthenticationService.COOKIE_NAME)
                                                .setValue(authToken)
                                )
                )
        );
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.USER_LOGIN;
    }
}
