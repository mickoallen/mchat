package com.mick.mchat.conversation;

import com.mick.mchat.websocket.inbound.InboundMessage;
import com.mick.mchat.websocket.inbound.InboundMessageHandler;
import com.mick.mchat.websocket.inbound.InboundMessageType;
import com.mick.mchat.websocket.inbound.messages.ConversationGetMessage;
import com.mick.mchat.websocket.outbound.OutboundMessage;
import com.mick.mchat.websocket.outbound.messages.ConversationsOutboundMessage;
import io.javalin.websocket.WsContext;
import kotlin.jvm.functions.Function2;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Singleton
public class ConversationMessageHandler implements InboundMessageHandler {

    private final ConversationService conversationService;

    @Inject
    public ConversationMessageHandler(final ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @Override
    public Map<InboundMessageType, Function2<? extends InboundMessage, WsContext, Optional<? extends OutboundMessage>>> getHandlerMap() {
        return Map.ofEntries(
                Map.entry(InboundMessageType.CONVERSATION_GET, (inboundMessage, wsContext) -> handleConversationsGet((ConversationGetMessage) inboundMessage))
        );
    }

    public Optional<ConversationsOutboundMessage> handleConversationsGet(ConversationGetMessage conversationsGetMessage) {
        List<Conversation> conversations;

        if(conversationsGetMessage.getConversationUuid().isPresent()){
            conversations = List.of(
                    conversationService.getConversation(
                            conversationsGetMessage.getConversationUuid().get(),
                            conversationsGetMessage.getUserUuid()
                    )
            );
        }else{
            conversations = conversationService.getAllConversationsForUser(conversationsGetMessage.getUserUuid());
        }


        return Optional.of(new ConversationsOutboundMessage().setConversations(conversations));
    }
}
