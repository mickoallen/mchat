package com.mick.mchat.handlers.chat;

import com.mick.mchat.handlers.chat.in.ChatMessageIn;
import com.mick.mchat.handlers.chat.in.UserTypingIn;
import com.mick.mchat.handlers.conversation.ConversationService;
import com.mick.mchat.jooq.model.tables.daos.MessageDao;
import com.mick.mchat.jooq.model.tables.pojos.Message;
import com.mick.mchat.jooq.model.tables.pojos.UserConversation;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.WsContextStore;
import com.mick.mchat.websocket.outbound.OutMessageType;
import com.mick.mchat.websocket.outbound.OutMessageWrapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;

@Singleton
public class ChatMessageService {
    private final MessageDao messageDao;
    private final int messageFetchLimit = 100;
    private final WsContextStore wsContextStore;
    private final ConversationService conversationService;

    @Inject
    public ChatMessageService(
            final MessageDao messageDao,
            final WsContextStore wsContextStore,
            final ConversationService conversationService) {
        this.messageDao = messageDao;
        this.wsContextStore = wsContextStore;
        this.conversationService = conversationService;
    }

    public Map<UUID, List<Message>> getMessagesForConversations(List<UUID> conversationUuids, long messageOffset) {
        return conversationUuids
                .stream()
                .map(conversationUuid ->
                        Map.entry(
                                conversationUuid,
                                messageDao
                                        .configuration()
                                        .dsl()
                                        .selectFrom(messageDao.getTable())
                                        .where(
                                                field(com.mick.mchat.jooq.model.tables.Message.MESSAGE.CONVERSATION_UUID).eq(conversationUuid)
                                        )
                                        .orderBy(field(com.mick.mchat.jooq.model.tables.Message.MESSAGE.DATE_CREATED).desc())
                                        .limit(messageFetchLimit)
                                        .fetchInto(Message.class)
                        )
                )
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
    }

    public void newInboundMessage(ChatMessageIn inboundChatMessage, AuthenticationToken authenticationToken) {

        Message message = new Message();
        message.setBody(inboundChatMessage.getMessage());
        message.setUserUuid(authenticationToken.getUserUuid());
        message.setConversationUuid(inboundChatMessage.getConversationUuid());
        message.setDateCreated(Timestamp.from(Instant.now()));
        message.setUuid(UUID.randomUUID());
        message.setType("TEXT");

        messageDao.insert(message);

        List<UserConversation> userConversations = conversationService.getUserConversations(List.of(inboundChatMessage.getConversationUuid()));

        OutMessageWrapper outMessageWrapper = OutMessageWrapper.of(OutMessageType.CHAT_MESSAGE)
                .body(ChatMessageMapper.toChatMessageOut(message));

        wsContextStore.getWsContextForUsers(
                userConversations
                        .stream()
                        .map(UserConversation::getUserUuid)
                        .collect(Collectors.toSet())
        )
                .forEach(wsContext -> wsContext.send(outMessageWrapper));
    }

    public void userIsTyping(UserTypingIn userTypingIn, AuthenticationToken authenticationToken) {
        List<UserConversation> userConversations = conversationService.getUserConversations(List.of(userTypingIn.getConversationUuid()));

        OutMessageWrapper outMessageWrapper = OutMessageWrapper.of(OutMessageType.USER_TYPING)
                .body(ChatMessageMapper.toTypingMessageOut(
                        authenticationToken.getUserUuid(),
                        userTypingIn.getConversationUuid()
                        )
                );

        wsContextStore.getWsContextForUsers(
                userConversations
                        .stream()
                        .map(UserConversation::getUserUuid)
                        .collect(Collectors.toSet())
        )
                .forEach(wsContext -> wsContext.send(outMessageWrapper));
    }
}
