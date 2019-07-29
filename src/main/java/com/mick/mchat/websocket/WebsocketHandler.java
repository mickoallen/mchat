package com.mick.mchat.websocket;

import com.mick.mchat.error.AuthenticationFailedException;
import com.mick.mchat.user.model.UserConnectedMessage;
import com.mick.mchat.user.security.AuthenticationService;
import com.mick.mchat.user.security.AuthenticationToken;
import com.mick.mchat.websocket.model.MessageContext;
import com.mick.mchat.websocket.model.WebsocketMessage;
import io.javalin.websocket.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.util.Optional;

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

        Optional<AuthenticationToken> authenticationToken = getValidAuthenticationToken(wsConnectContext);

        if (authenticationToken.isEmpty()) {
            return;
        }

        wsContextStore.addUserWsContext(authenticationToken.get().getUserUuid(), wsConnectContext);

        WebsocketMessage<UserConnectedMessage> websocketMessage = new WebsocketMessage<>();
        websocketMessage.setBody(new UserConnectedMessage());
        websocketMessage.setContext(new MessageContext().setUserUuid(authenticationToken.get().getUserUuid()));

        messageDispatcher.dispatchMessage(websocketMessage, wsConnectContext);
    }

    @Override
    public void handleClose(@NotNull WsCloseContext wsCloseContext) throws Exception {
        //SecureUser secureUser = secureUserExtractor.getSecureUser(wsCloseContext.cookie("mchat"));
//        wsContextStore.removeUserWsContext(secureUser.getUser().getUuid());
    }

    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) throws Exception {
        Optional<AuthenticationToken> authenticationTokenOptional = getValidAuthenticationToken(wsMessageContext);

        AuthenticationToken authenticationToken = authenticationTokenOptional.orElseThrow(() -> new AuthenticationFailedException("User is not authenticated"));

        WebsocketMessage websocketMessage = WebsocketMessageMapper.deserialize(wsMessageContext.message());

        if (websocketMessage.getContext() == null) {
            websocketMessage.setContext(
                    new MessageContext()
                            .setDateCreated(Instant.now().toEpochMilli())
            );
        }

        websocketMessage.getContext().setUserUuid(authenticationToken.getUserUuid());

        messageDispatcher.dispatchMessage(websocketMessage, wsMessageContext);
    }

    @Override
    public void handleError(@NotNull WsErrorContext wsErrorContext) throws Exception {
        wsErrorContext.toString();
        //wat
    }

    private Optional<AuthenticationToken> getValidAuthenticationToken(WsContext wsContext) {
        String jwtCookie = wsContext.cookie(AuthenticationService.COOKIE_NAME);

        if (jwtCookie == null) {
            //no cookie present on connect, just chill i guess
            return Optional.empty();
        }

        return Optional.of(authenticationService.getValidAuthenticationToken(jwtCookie));
    }
}
