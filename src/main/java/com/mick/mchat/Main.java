package com.mick.mchat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mick.mchat.websocket.WebsocketHandler;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.get;

/**
 * Run this to start it, obviously.
 */
public class Main {
    public static void main(String[] args) {
        Injector guiceInjector = Guice.createInjector(new MChatModule());

        WebsocketHandler mChatWsHandler = guiceInjector.getInstance(WebsocketHandler.class);

        Javalin.create()
                .ws("/ws", ws -> {
                    ws.onConnect(mChatWsHandler);
                    ws.onClose(mChatWsHandler);
                    ws.onError(mChatWsHandler);
                    ws.onMessage(mChatWsHandler);
                }).routes(() ->
                get("/health", (context) -> {
                    context.result("Your mom is good").status(200);
                })
        ).start(7070);
    }
}
