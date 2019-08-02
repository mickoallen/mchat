package com.mick.mchat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mick.mchat.websocket.inbound.MChatWebsocketHandler;
import io.javalin.Javalin;

/**
 * Run this to start it, obviously.
 */
public class Main {
    public static void main(String[] args) {
        Injector guiceInjector = Guice.createInjector(new MChatModule());

        MChatWebsocketHandler mChatWsHandler = guiceInjector.getInstance(MChatWebsocketHandler.class);
        HealthHandler healthHandler = guiceInjector.getInstance(HealthHandler.class);
        Javalin.create()
                .ws("/ws", ws -> {
                    ws.onConnect(mChatWsHandler);
                    ws.onClose(mChatWsHandler);
                    ws.onError(mChatWsHandler);
                    ws.onMessage(mChatWsHandler);
                })
                .get("/health/users", context -> context.result(healthHandler.getConnectedUsers()).status(200))
                .get("/health", context -> context.result(healthHandler.getHealth()).status(200))
                .start(7070);
    }
}
