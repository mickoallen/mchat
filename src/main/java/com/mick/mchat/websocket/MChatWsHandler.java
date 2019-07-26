package com.mick.mchat.websocket;

import com.mick.mchat.user.SecureUser;
import com.mick.mchat.user.SecureUserExtractor;
import com.mick.mchat.user.User;
import com.mick.mchat.user.ws.UserConnectedMessage;
import io.javalin.websocket.*;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class MChatWsHandler implements WsConnectHandler, WsCloseHandler, WsErrorHandler, WsMessageHandler {
    private final WsContextStore wsContextStore;
    private final SecureUserExtractor secureUserExtractor;
    private final MessageDispatcher messageDispatcher;

    @Inject
    public MChatWsHandler(
            final WsContextStore wsContextStore,
            final SecureUserExtractor secureUserExtractor,
            final MessageDispatcher messageDispatcher
    ) {
        this.wsContextStore = wsContextStore;
        this.secureUserExtractor = secureUserExtractor;
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public void handleConnect(@NotNull WsConnectContext wsConnectContext) throws Exception {
        SecureUser secureUser = secureUserExtractor.getSecureUser(wsConnectContext.cookie("mchat"));
        //todo give every connection a random user
        User user = new User().setUuid(UUID.randomUUID());
        wsContextStore.addUserWsContext(user.getUuid(), wsConnectContext);

        UserConnectedMessage userConnectedMessage = new UserConnectedMessage();
        userConnectedMessage.setUserUuid(user.getUuid());

        messageDispatcher.dispatchMessage(user, userConnectedMessage);
    }

    @Override
    public void handleClose(@NotNull WsCloseContext wsCloseContext) throws Exception {
        //SecureUser secureUser = secureUserExtractor.getSecureUser(wsCloseContext.cookie("mchat"));
//        wsContextStore.removeUserWsContext(secureUser.getUser().getUuid());
    }

    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) throws Exception {
        SecureUser secureUser = secureUserExtractor.getSecureUser(wsMessageContext.cookie("mchat"));
        messageDispatcher.dispatchMessage(secureUser.getUser(), WebsocketMessageMapper.deserialize(wsMessageContext.message()));
    }

    @Override
    public void handleError(@NotNull WsErrorContext wsErrorContext) throws Exception {
        wsErrorContext.toString();
        //wat
    }
}
