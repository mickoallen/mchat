package com.mick.mchat.handlers.chat;

import com.mick.mchat.handlers.chat.in.ChatMessageIn;
import com.mick.mchat.handlers.chat.out.ChatMessageType;
import com.mick.mchat.handlers.conversation.ConversationService;
import com.mick.mchat.jooq.model.tables.pojos.Message;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.util.TimeProvider;
import com.mick.mchat.websocket.outbound.OutMessageType;
import com.mick.mchat.websocket.outbound.OutMessageWrapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Business logic for handling chat messages
 */
@Singleton
public class ChatMessageService {

    private static final int messageFetchLimit = 40;//todo make configurable

    private final ChatMessageRepository chatMessageRepository;
    private final ConversationService conversationService;
    private final TimeProvider timeProvider;

    @Inject
    public ChatMessageService(
            final ChatMessageRepository chatMessageRepository,
            final ConversationService conversationService,
            final TimeProvider timeProvider) {
        this.chatMessageRepository = chatMessageRepository;
        this.conversationService = conversationService;
        this.timeProvider = timeProvider;
    }

    public Map<UUID, List<Message>> getMessagesForConversations(final List<UUID> conversationUuids) {
        return conversationUuids
                .stream()
                .map(conversationUuid ->
                        Map.entry(
                                conversationUuid,
                                chatMessageRepository.getMessagesForConversation(conversationUuid, messageFetchLimit)
                        )
                )
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
    }

    public void handleInboundMessage(ChatMessageIn inboundChatMessage, AuthenticationToken authenticationToken) {
        Message message = new Message();
        message.setBody(inboundChatMessage.getMessage());
        message.setUserUuid(authenticationToken.getUserUuid());
        message.setConversationUuid(inboundChatMessage.getConversationUuid());
        message.setDateCreated(timeProvider.getNowAsTimestamp());
        message.setUuid(UUID.randomUUID());
        message.setType(ChatMessageType.TEXT.name()); //default for now

        chatMessageRepository.create(message);

        conversationService.sendMessageToUsersInConversation(
                inboundChatMessage.getConversationUuid(),
                OutMessageWrapper.of(OutMessageType.CHAT_MESSAGE)
                        .body(ChatMessageMapper.toChatMessageOut(message))
        );
    }
}
