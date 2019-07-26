package com.mick.mchat.websocket;

import com.mick.mchat.conversation.ws.ChatMessageHandler;
import com.mick.mchat.conversation.ws.WebsocketChatMessage;
import com.mick.mchat.user.User;
import com.mick.mchat.user.UserConnectedHandler;
import com.mick.mchat.user.ws.UserConnectedMessage;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Could use some in mem message broker instead. For this to scale to multiple nodes we'll need that.
 */
@Singleton
public class MessageDispatcher {

    private final ChatMessageHandler chatMessageHandler;
    private final UserConnectedHandler userConnectedHandler;

    @Inject
    public MessageDispatcher(ChatMessageHandler chatMessageHandler, UserConnectedHandler userConnectedHandler) {
        this.chatMessageHandler = chatMessageHandler;
        this.userConnectedHandler = userConnectedHandler;
    }

    public void dispatchMessage(User user, WebsocketMessage websocketMessage){
        try {
            switch (websocketMessage.getWebsocketMessageType()) {
                case CHAT_MESSAGE:
                    chatMessageHandler.handleMessage((WebsocketChatMessage) websocketMessage);
                    break;
                case USER_CONNECTED:
                    userConnectedHandler.handleMessage((UserConnectedMessage) websocketMessage);
                    break;
                case USER_DISCONNECTED:
                    break;
                case NOTIFICATION:
                    break;
            }
        }catch (Exception e){
            //log something
            throw new RuntimeException(e);
        }
    }
}
