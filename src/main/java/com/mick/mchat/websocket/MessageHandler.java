package com.mick.mchat.websocket;

import com.mick.mchat.error.MChatException;
import com.mick.mchat.websocket.model.MessageBody;
import com.mick.mchat.websocket.model.MessageType;
import com.mick.mchat.websocket.model.WebsocketMessage;
import io.javalin.websocket.WsContext;

public interface MessageHandler<T extends MessageBody> {
    void handleMessage(WebsocketMessage<T> websocketMessage, WsContext wsContext) throws MChatException;
    MessageType getMessageType();
}
