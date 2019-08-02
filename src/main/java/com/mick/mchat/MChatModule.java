package com.mick.mchat;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.mick.mchat.handlers.chat.in.ChatMessageHandler;
import com.mick.mchat.handlers.conversation.in.ConversationMessageHandler;
import com.mick.mchat.handlers.user.in.UserMessageHandler;
import com.mick.mchat.websocket.inbound.InMessageHandler;

import java.util.Set;

/**
 * Magic, bitches.
 */
public class MChatModule extends AbstractModule {

    @Override
    protected void configure() {
        /* Register all the message handlers here */
        Set<Class<? extends InMessageHandler>> messageHandlers = Set.of(
                UserMessageHandler.class,
                ConversationMessageHandler.class,
                ChatMessageHandler.class
        );

        Multibinder<InMessageHandler> messageHandlerMultibinder = Multibinder.newSetBinder(binder(), InMessageHandler.class);

        messageHandlers.forEach(messageHandler -> messageHandlerMultibinder.addBinding().to(messageHandler));
    }
}
