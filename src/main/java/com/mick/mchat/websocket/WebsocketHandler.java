package com.mick.mchat.websocket;

import com.mick.mchat.user.model.User;
import com.mick.mchat.user.model.UserConnectedMessage;
import com.mick.mchat.user.security.AuthenticationService;
import com.mick.mchat.user.security.AuthenticationToken;
import com.mick.mchat.websocket.model.WebsocketMessage;
import io.javalin.websocket.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class WebsocketHandler implements WsConnectHandler, WsCloseHandler, WsErrorHandler, WsMessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketHandler.class);
    private final WsContextStore wsContextStore;
    private final MessageDispatcher messageDispatcher;
    private final AuthenticationService authenticationService;

    @Inject
    public WebsocketHandler(
            final WsContextStore wsContextStore,
            final MessageDispatcher messageDispatcher,
            final AuthenticationService authenticationService) {
        this.wsContextStore = wsContextStore;
        this.messageDispatcher = messageDispatcher;
        this.authenticationService = authenticationService;
    }

    @Override
    public void handleConnect(@NotNull WsConnectContext wsConnectContext) throws Exception {
        logger.info("Websocket connection established: {}", wsConnectContext);

        String jwtCookie = wsConnectContext.cookie(AuthenticationService.COOKIE_NAME);

        if(jwtCookie == null){
            //no cookie present on connect, just chill i guess
            return;
        }

        AuthenticationToken authenticationToken = authenticationService.getValidAuthenticationToken(jwtCookie);

        //if there is a valid auth, ensure the user still exists and is allowed to do shit - only do this on connect
        //if they really want to maintain a ws connection and talk very that's cool bro
        User user = new User().setUuid(UUID.randomUUID());
        wsContextStore.addUserWsContext(user.getUuid(), wsConnectContext);

        WebsocketMessage<UserConnectedMessage> websocketMessage = new WebsocketMessage<>();
        websocketMessage.setBody(new UserConnectedMessage());

        messageDispatcher.dispatchMessage(websocketMessage, wsConnectContext);
    }

    @Override
    public void handleClose(@NotNull WsCloseContext wsCloseContext) throws Exception {
        //SecureUser secureUser = secureUserExtractor.getSecureUser(wsCloseContext.cookie("mchat"));
//        wsContextStore.removeUserWsContext(secureUser.getUser().getUuid());
    }

    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) throws Exception {
        messageDispatcher.dispatchMessage(WebsocketMessageMapper.deserialize(wsMessageContext.message()), wsMessageContext);
    }

    @Override
    public void handleError(@NotNull WsErrorContext wsErrorContext) throws Exception {
        wsErrorContext.toString();
        //wat
    }
}
