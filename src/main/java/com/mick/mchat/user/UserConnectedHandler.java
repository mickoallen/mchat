package com.mick.mchat.user;

import com.mick.mchat.error.MChatException;
import com.mick.mchat.websocket.MessageHandler;
import com.mick.mchat.conversation.ConversationRepository;
import com.mick.mchat.user.model.UserConnectedMessage;
import com.mick.mchat.websocket.model.MessageType;
import com.mick.mchat.websocket.model.WebsocketMessage;
import com.mick.mchat.websocket.WsContextStore;
import io.javalin.websocket.WsContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class UserConnectedHandler implements MessageHandler<UserConnectedMessage> {
    private final WsContextStore wsContextStore;
    private final ConversationRepository conversationRepository;

    @Inject
    public UserConnectedHandler(WsContextStore wsContextStore, ConversationRepository conversationRepository) {
        this.wsContextStore = wsContextStore;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public void handleMessage(WebsocketMessage<UserConnectedMessage> websocketMessage, WsContext wsContext) throws MChatException {
        Collection<WsContext> allWsContexts = wsContextStore.getAllWsContexts();

        UserConnectedMessage userConnectedMessage = websocketMessage.getBody();

        //for now just notify everyone
        allWsContexts.forEach(currentConnections -> currentConnections.send(userConnectedMessage));

        conversationRepository.addUserToConversation(websocketMessage.getInfo().getUserUuid());
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.USER_CONNECTED;
    }
}
