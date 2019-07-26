package com.mick.mchat.conversation.ws;

import com.mick.mchat.MChatException;
import com.mick.mchat.websocket.MessageHandler;
import com.mick.mchat.websocket.WebsocketMessageMapper;
import com.mick.mchat.conversation.ChatMessageRepository;
import com.mick.mchat.conversation.Conversation;
import com.mick.mchat.conversation.ConversationStore;
import com.mick.mchat.websocket.WsContextStore;
import io.javalin.websocket.WsContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

@Singleton
public class ChatMessageHandler implements MessageHandler<WebsocketChatMessage> {
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
    public void handleMessage(WebsocketChatMessage websocketMessage) throws MChatException {
        Conversation conversation = conversationStore.getConversation(websocketMessage.getConversationUuid());

        List<WsContext> wsContextForUsers = wsContextStore.getWsContextForUsers(conversation.getParticipants());

        wsContextForUsers.forEach(wsContext -> {
                    try {
                        wsContext.send(WebsocketMessageMapper.serialize(websocketMessage));
                    } catch (IOException exception) {
                        //sheet
                    }
                }
        );
    }
}
