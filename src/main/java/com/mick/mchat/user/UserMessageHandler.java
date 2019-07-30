package com.mick.mchat.user;

import com.mick.mchat.error.AuthenticationFailedException;
import com.mick.mchat.user.security.AuthenticationService;
import com.mick.mchat.websocket.inbound.InboundMessage;
import com.mick.mchat.websocket.inbound.InboundMessageHandler;
import com.mick.mchat.websocket.inbound.InboundMessageType;
import com.mick.mchat.websocket.inbound.messages.UserLoginMessage;
import com.mick.mchat.websocket.outbound.messages.LoginResponseMessage;
import com.mick.mchat.websocket.outbound.OutboundMessage;
import io.javalin.websocket.WsContext;
import kotlin.jvm.functions.Function2;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;

@Singleton
public class UserMessageHandler implements InboundMessageHandler {
    private final UserService userService;

    @Inject
    public UserMessageHandler(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public Map<InboundMessageType, Function2<? extends InboundMessage, WsContext, Optional<? extends OutboundMessage>>> getHandlerMap() {
        return Map.of(
                InboundMessageType.USER_LOGIN, (inboundMessage, wsContext) -> login((UserLoginMessage)inboundMessage)
        );
    }

    public Optional<LoginResponseMessage> login(UserLoginMessage userLoginMessage) {
        String authenticationToken;
        try {
            authenticationToken = userService.login(userLoginMessage.getUsername(), userLoginMessage.getPassword());
        }catch (AuthenticationFailedException e){
            LoginResponseMessage loginResponseMessage = new LoginResponseMessage();
            loginResponseMessage.setStatus(HttpStatus.UNAUTHORIZED_401);

            return Optional.of(
                    loginResponseMessage
            );
        }

        return Optional.of(
                new LoginResponseMessage()
                        .setAuthenticationToken(authenticationToken)
        );
    }
}
