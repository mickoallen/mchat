package com.mick.mchat.websocket;

import com.mick.mchat.error.MChatException;
import com.mick.mchat.websocket.model.MessageType;
import com.mick.mchat.websocket.model.WebsocketMessage;
import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

/**
 * Could use some in mem message broker instead. For this to scale to multiple nodes we'll need that.
 */
@Singleton
public class MessageDispatcher {
    private final static Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    private final Map<MessageType, MessageHandler> messageHandlerMap;

    @Inject
    public MessageDispatcher(Map<MessageType, MessageHandler> messageHandlerMap) {
        this.messageHandlerMap = messageHandlerMap;
    }

    public void dispatchMessage(WebsocketMessage websocketMessage, WsContext wsContext) {

        MessageHandler<?> messageHandler = messageHandlerMap.get(websocketMessage.getBody().getMessageType());
        try {
            //noinspection unchecked
            messageHandler.handleMessage(websocketMessage, wsContext);
        } catch (MChatException e) {
            logger.error("Exception handling message: {}", websocketMessage, e);
            //todo - return some error to ui?
        }
    }
}
