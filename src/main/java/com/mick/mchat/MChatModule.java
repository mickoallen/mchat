package com.mick.mchat;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.mick.mchat.conversation.ConversationMessageHandler;
import com.mick.mchat.user.UserConnectedHandlerInbound;
import com.mick.mchat.user.UserMessageHandler;
import com.mick.mchat.websocket.inbound.InboundMessageHandler;

import java.util.List;

/**
 * Magic, bitches.
 */
public class MChatModule extends AbstractModule {

    @Override
    protected void configure() {
        /* Register all the message handlers here */
        List<Class<? extends InboundMessageHandler>> messageHandlers = List.of(
                UserConnectedHandlerInbound.class,
                UserMessageHandler.class,
                ConversationMessageHandler.class
        );

        Multibinder<InboundMessageHandler> messageHandlerMultibinder = Multibinder.newSetBinder(binder(), InboundMessageHandler.class);

        messageHandlers.forEach(messageHandler -> messageHandlerMultibinder.addBinding().to(messageHandler));
    }
}
