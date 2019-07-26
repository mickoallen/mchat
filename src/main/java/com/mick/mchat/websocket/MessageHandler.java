package com.mick.mchat.websocket;

import com.mick.mchat.MChatException;

public interface MessageHandler<T> {
    void handleMessage(T websocketMessage) throws MChatException;
}
