package com.mick.mchat.websocket;

import com.mick.mchat.error.AuthenticationFailedException;
import com.mick.mchat.user.security.AuthenticationService;
import com.mick.mchat.user.security.AuthenticationToken;
import com.mick.mchat.websocket.inbound.InboundMessage;
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
        logger.info("Websocket connection established: {}", wsConnectContext);

        Optional<AuthenticationToken> authenticationToken = getValidAuthenticationToken(wsConnectContext);

        if (authenticationToken.isEmpty()) {
            return;
        }

        wsContextStore.addUserWsContext(authenticationToken.get().getUserUuid(), wsConnectContext);
        //todo
    }

    @Override
    public void handleClose(@NotNull WsCloseContext wsCloseContext) throws Exception {
        //SecureUser secureUser = secureUserExtractor.getSecureUser(wsCloseContext.cookie("mchat"));
//        wsContextStore.removeUserWsContext(secureUser.getUser().getUuid());
    }

    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) throws Exception {
        InboundMessage websocketMessage = MessageCodec.deserialize(wsMessageContext.message());

        if (websocketMessage.getType().isAuthenticationRequired()) {
            Optional<AuthenticationToken> authenticationTokenOptional = getValidAuthenticationToken(wsMessageContext);
            AuthenticationToken authenticationToken = authenticationTokenOptional.orElseThrow(() -> new AuthenticationFailedException("User is not authenticated"));
            websocketMessage.setUserUuid(authenticationToken.getUserUuid());
        }

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
