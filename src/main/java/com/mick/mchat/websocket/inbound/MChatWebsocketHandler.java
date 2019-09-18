package com.mick.mchat.websocket.inbound;

import com.mick.mchat.error.AuthenticationFailedException;
import com.mick.mchat.security.AuthenticationService;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.WsContextStore;
import io.javalin.websocket.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class MChatWebsocketHandler implements WsConnectHandler, WsCloseHandler, WsErrorHandler, WsMessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(MChatWebsocketHandler.class);
    private final WsContextStore wsContextStore;
    private final MessageDispatcher messageDispatcher;
    private final AuthenticationService authenticationService;

    @Inject
    public MChatWebsocketHandler(
            final WsContextStore wsContextStore,
            final MessageDispatcher messageDispatcher,
            final AuthenticationService authenticationService) {
        this.wsContextStore = wsContextStore;
        this.messageDispatcher = messageDispatcher;
        this.authenticationService = authenticationService;
    }

    @Override
    public void handleConnect(@NotNull WsConnectContext wsConnectContext) throws Exception {
        logger.debug("Websocket connection established: {}", wsConnectContext);

        Optional<AuthenticationToken> authenticationToken = getValidAuthenticationToken(wsConnectContext);

        if (authenticationToken.isEmpty()) {
            return;
        }

        logger.debug("User still has authentication token in cookie, reconnecting them.");

        wsContextStore.addUserWsContext(authenticationToken.get().getUserUuid(), wsConnectContext);
    }

    @Override
    public void handleClose(@NotNull WsCloseContext wsCloseContext) throws Exception {
        logger.debug("Websocket connection closed");
        wsContextStore.removeUserWsContextForSessionId(wsCloseContext.getSessionId());
    }

    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) throws Exception {
        try {
            messageDispatcher.dispatchMessage(wsMessageContext.message(), wsMessageContext);
        } catch (AuthenticationFailedException e) {
            logger.error("Failed to get authentication token from message: {}", wsMessageContext.message(), e);
        }
    }

    @Override
    public void handleError(@NotNull WsErrorContext wsErrorContext) throws Exception {
        logger.error("Websocket error", wsErrorContext.error());
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
