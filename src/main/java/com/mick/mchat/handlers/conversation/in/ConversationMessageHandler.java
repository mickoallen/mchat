package com.mick.mchat.handlers.conversation.in;

import com.mick.mchat.handlers.conversation.Conversation;
import com.mick.mchat.handlers.conversation.ConversationService;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.inbound.InMessageHandler;
import com.mick.mchat.websocket.inbound.InMessageType;
import com.mick.mchat.websocket.inbound.MChatMessageHandler;
import com.mick.mchat.websocket.outbound.OutMessageType;
import com.mick.mchat.handlers.conversation.out.ConversationsOut;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ConversationMessageHandler implements InMessageHandler {

    private final ConversationService conversationService;

    @Inject
    public ConversationMessageHandler(final ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @MChatMessageHandler(
            inType = InMessageType.ADD_USERS_TO_CONVERSATION
    )
    public void createOrUpdateConversation(AddUsersToConversationIn inboundMessage) {
        //get conversation
        //create if it doesn't exist

        //add users to conversation
    }

    @MChatMessageHandler(
            inType = InMessageType.CONVERSATIONS_GET_ALL,
            outType = OutMessageType.CONVERSATIONS_GET_ALL_RESPONSE
    )
    public ConversationsOut handleConversationsGet(ConversationGetIn conversationsGetMessage, AuthenticationToken authenticationToken) {
        List<Conversation> conversations = conversationService.getAllConversationsForUser(authenticationToken.getUserUuid());

        return new ConversationsOut().setConversations(conversations);
    }
}
