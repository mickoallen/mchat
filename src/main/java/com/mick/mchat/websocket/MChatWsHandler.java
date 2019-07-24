package com.mick.mchat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mick.mchat.WsMessage;
import com.mick.mchat.user.SecureUser;
import com.mick.mchat.user.SecureUserExtractor;
import io.javalin.websocket.*;
import org.jetbrains.annotations.NotNull;

public class MChatWsHandler implements WsConnectHandler, WsCloseHandler, WsErrorHandler, WsMessageHandler {
    private final WsContextStore wsContextStore;
    private final SecureUserExtractor secureUserExtractor;
    private final MessageDispatcher messageDispatcher;
    private final ObjectMapper objectMapper;

    public MChatWsHandler(
            final WsContextStore wsContextStore,
            final SecureUserExtractor secureUserExtractor,
            final MessageDispatcher messageDispatcher
    ) {
        this.wsContextStore = wsContextStore;
        this.secureUserExtractor = secureUserExtractor;
        this.messageDispatcher = messageDispatcher;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void handleConnect(@NotNull WsConnectContext wsConnectContext) throws Exception {
        SecureUser secureUser = secureUserExtractor.getSecureUser(wsConnectContext.cookie("mchat"));
        wsContextStore.addUserWsContext(secureUser.getUser().getUuid(), wsConnectContext);
    }

    @Override
    public void handleClose(@NotNull WsCloseContext wsCloseContext) throws Exception {
        SecureUser secureUser = secureUserExtractor.getSecureUser(wsCloseContext.cookie("mchat"));
        wsContextStore.removeUserWsContext(secureUser.getUser().getUuid());
    }

    @Override
    public void handleError(@NotNull WsErrorContext wsErrorContext) throws Exception {
        //wat
    }

    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) throws Exception {
        SecureUser secureUser = secureUserExtractor.getSecureUser(wsMessageContext.cookie("mchat"));
        messageDispatcher.dispatchMessage(secureUser.getUser(), objectMapper.readValue(wsMessageContext.message(), WsMessage.class));
    }
}
