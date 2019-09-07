package com.mick.mchat.handlers.conversation;

import com.mick.mchat.handlers.chat.ChatMessageService;
import com.mick.mchat.handlers.conversation.in.ConversationGetIn;
import com.mick.mchat.handlers.conversation.in.CreateConversationIn;
import com.mick.mchat.handlers.conversation.out.ConversationsOut;
import com.mick.mchat.jooq.model.tables.pojos.Conversation;
import com.mick.mchat.jooq.model.tables.pojos.Message;
import com.mick.mchat.jooq.model.tables.pojos.UserConversation;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.WsContextStore;
import com.mick.mchat.websocket.inbound.InMessageHandler;
import com.mick.mchat.websocket.inbound.InMessageType;
import com.mick.mchat.websocket.inbound.MChatMessageHandler;
import com.mick.mchat.websocket.outbound.OutMessageType;
import com.mick.mchat.websocket.outbound.OutMessageWrapper;

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
    private final WsContextStore wsContextStore;

    @Inject
    public ConversationMessageHandler(
            final ConversationService conversationService,
            final ChatMessageService chatMessageService,
            final WsContextStore wsContextStore) {
        this.conversationService = conversationService;
        this.chatMessageService = chatMessageService;
        this.wsContextStore = wsContextStore;
    }

    @MChatMessageHandler(
            inType = InMessageType.CREATE_CONVERSATION
    )
    public void createConversation(CreateConversationIn createConversationIn, AuthenticationToken authenticationToken) {
        //add current user to conversation
        createConversationIn.getUsers()
                .add(authenticationToken.getUserUuid());
        //add the requested users to the conversation
        createConversationIn.getUsers().addAll(createConversationIn.getUsers());

        Conversation conversation = new Conversation();
        conversation.setName(createConversationIn.getName());

        conversationService.createConversationForUsers(conversation, createConversationIn.getUsers());

        //send this new conversation out to each of the participants
        for (UUID userUuid : createConversationIn.getUsers()) {
            wsContextStore.getWsContextForUsers(List.of(userUuid))
                    .forEach(wsContext -> wsContext.send(
                            OutMessageWrapper.of(OutMessageType.CONVERSATIONS_GET_ALL_RESPONSE)
                                    .body(getAllConversationsForUser(
                                            new ConversationGetIn(),
                                            new AuthenticationToken().setUserUuid(userUuid))
                                    )
                            )

                    );
        }
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
