package com.mick.mchat.handlers.conversation;

import com.mick.mchat.handlers.chat.ChatMessageService;
import com.mick.mchat.handlers.conversation.in.CreateConversationIn;
import com.mick.mchat.handlers.conversation.in.ConversationGetIn;
import com.mick.mchat.handlers.conversation.out.ConversationsOut;
import com.mick.mchat.jooq.model.tables.pojos.Conversation;
import com.mick.mchat.jooq.model.tables.pojos.Message;
import com.mick.mchat.jooq.model.tables.pojos.UserConversation;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.inbound.InMessageHandler;
import com.mick.mchat.websocket.inbound.InMessageType;
import com.mick.mchat.websocket.inbound.MChatMessageHandler;
import com.mick.mchat.websocket.outbound.OutMessageType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class ConversationMessageHandler implements InMessageHandler {

    private final ConversationService conversationService;
    private final ChatMessageService chatMessageService;

    @Inject
    public ConversationMessageHandler(
            final ConversationService conversationService,
            final ChatMessageService chatMessageService
    ) {
        this.conversationService = conversationService;
        this.chatMessageService = chatMessageService;
    }

    @MChatMessageHandler(
            inType = InMessageType.CREATE_CONVERSATION,
            outType = OutMessageType.CONVERSATIONS_GET_ALL_RESPONSE
    )
    public ConversationsOut createConversation(CreateConversationIn createConversationIn, AuthenticationToken authenticationToken) {
        createConversationIn.getUsers().add(authenticationToken.getUserUuid());//add requesting user to conversation
        conversationService.createConversationForUsers(createConversationIn.getUsers());

        //todo send notification to other users!
        return getAllConversationsForUser(new ConversationGetIn(), authenticationToken);
    }

    @MChatMessageHandler(
            inType = InMessageType.CONVERSATIONS_GET_ALL,
            outType = OutMessageType.CONVERSATIONS_GET_ALL_RESPONSE
    )
    public ConversationsOut getAllConversationsForUser(ConversationGetIn conversationsGetMessage, AuthenticationToken authenticationToken) {
        //todo - this isnt very efficient, will look into it later
        List<Conversation> conversations = conversationService.getAllConversationsForUser(authenticationToken.getUserUuid());
        List<UserConversation> userConversations = conversationService.getUserConversations(
                conversations
                        .stream()
                        .map(Conversation::getUuid)
                        .collect(Collectors.toList())
        );

        //get chat messages
        Map<UUID, List<Message>> messagesForConversations = chatMessageService.getMessagesForConversations(
                conversations
                        .stream()
                        .map(Conversation::getUuid)
                        .collect(Collectors.toList()),
                conversationsGetMessage.getMessageOffset()
        );

        return ConversationMapper.toConversationsOut(conversations, userConversations, messagesForConversations);
    }
}
