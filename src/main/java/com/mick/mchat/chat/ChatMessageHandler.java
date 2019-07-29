package com.mick.mchat.chat;

import com.mick.mchat.conversation.Conversation;
import com.mick.mchat.conversation.ConversationStore;
import com.mick.mchat.error.MChatException;
import com.mick.mchat.websocket.MessageHandler;
import com.mick.mchat.websocket.WebsocketMessageMapper;
import com.mick.mchat.websocket.WsContextStore;
import com.mick.mchat.websocket.model.MessageType;
import com.mick.mchat.websocket.model.WebsocketMessage;
import io.javalin.websocket.WsContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ChatMessageHandler implements MessageHandler<ChatMessageBody> {
    private final WsContextStore wsContextStore;
    private final ConversationStore conversationStore;
    private final ChatMessageRepository chatMessageRepository;

    @Inject
    public ChatMessageHandler(
            final WsContextStore wsContextStore,
            final ConversationStore conversationStore,
            final ChatMessageRepository chatMessageRepository
    ) {
        this.wsContextStore = wsContextStore;
        this.conversationStore = conversationStore;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public void handleMessage(WebsocketMessage<ChatMessageBody> websocketMessage, WsContext wsMessageContext) throws MChatException {
        Conversation conversation = conversationStore.getConversation(websocketMessage.getBody().getConversationUuid());

        List<WsContext> wsContextForUsers = wsContextStore.getWsContextForUsers(conversation.getParticipants());

        wsContextForUsers.forEach(wsContext -> {
                    wsContext.send(WebsocketMessageMapper.serialize(websocketMessage));
                }
        );
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CHAT_MESSAGE;
    }
}
