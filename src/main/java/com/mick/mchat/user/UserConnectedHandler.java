package com.mick.mchat.user;

import com.mick.mchat.MChatException;
import com.mick.mchat.websocket.MessageHandler;
import com.mick.mchat.conversation.ConversationRepository;
import com.mick.mchat.user.ws.UserConnectedMessage;
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
    public void handleMessage(UserConnectedMessage userConnectedMessage) throws MChatException {
        Collection<WsContext> allWsContexts = wsContextStore.getAllWsContexts();

        //for now just notify everyone
        allWsContexts.forEach(wsContext -> wsContext.send(userConnectedMessage));

        conversationRepository.addUserToConversation(userConnectedMessage.getUserUuid());
    }
}
