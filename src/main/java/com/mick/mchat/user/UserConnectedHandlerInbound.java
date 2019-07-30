package com.mick.mchat.user;

import com.mick.mchat.conversation.ConversationRepository;
import com.mick.mchat.websocket.inbound.messages.UserConnectedMessage;
import com.mick.mchat.websocket.WsContextStore;
import com.mick.mchat.websocket.inbound.InboundMessage;
import com.mick.mchat.websocket.inbound.InboundMessageHandler;
import com.mick.mchat.websocket.inbound.InboundMessageType;
import com.mick.mchat.websocket.outbound.OutboundMessage;
import io.javalin.websocket.WsContext;
import kotlin.jvm.functions.Function2;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Singleton
public class UserConnectedHandlerInbound implements InboundMessageHandler {
    private final WsContextStore wsContextStore;
    private final ConversationRepository conversationRepository;

    @Inject
    public UserConnectedHandlerInbound(WsContextStore wsContextStore, ConversationRepository conversationRepository) {
        this.wsContextStore = wsContextStore;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public Map<InboundMessageType, Function2<? extends InboundMessage, WsContext, Optional<? extends OutboundMessage>>> getHandlerMap() {
        return Map.of(
                InboundMessageType.USER_CONNECTED, (inboundMessage, wsContext) -> handle((UserConnectedMessage) inboundMessage)
        );
    }

    private Optional<OutboundMessage> handle(UserConnectedMessage userConnectedMessage) {
        Collection<WsContext> allWsContexts = wsContextStore.getAllWsContexts();

        //for now just notify everyone
        allWsContexts.forEach(currentConnections -> currentConnections.send(userConnectedMessage));

        conversationRepository.addUserToConversation(userConnectedMessage.getUserUuid());

        return Optional.empty();
    }


}
