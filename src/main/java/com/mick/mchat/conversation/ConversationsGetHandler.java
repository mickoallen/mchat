package com.mick.mchat.conversation;

import com.mick.mchat.error.MChatException;
import com.mick.mchat.websocket.MessageHandler;
import com.mick.mchat.websocket.WebsocketMessageMapper;
import com.mick.mchat.websocket.model.MessageType;
import com.mick.mchat.websocket.model.WebsocketMessage;
import io.javalin.websocket.WsContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.UUID;

@Singleton
public class ConversationsGetHandler implements MessageHandler<ConversationsGetMessage> {
    private final ConversationRepository conversationRepository;

    @Inject
    public ConversationsGetHandler(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public void handleMessage(WebsocketMessage<ConversationsGetMessage> websocketMessage, WsContext wsContext) throws MChatException {
        UUID userUuid = websocketMessage.getContext().getUserUuid();
        Collection<Conversation> conversationsForUser = conversationRepository.getConversationsForUser(userUuid);

        wsContext.send(WebsocketMessageMapper.serialize(conversationsForUser));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CONVERSATIONS_GET;
    }
}
