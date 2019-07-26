package com.mick.mchat;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.mick.mchat.chat.ChatMessageHandler;
import com.mick.mchat.user.UserConnectedHandler;
import com.mick.mchat.user.login.UserLoginHandler;
import com.mick.mchat.websocket.MessageHandler;
import com.mick.mchat.websocket.model.MessageType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Magic, bitches.
 */
public class MChatModule extends AbstractModule {

    @Override
    protected void configure() {
        /* Register all the message handlers here */
        List<Class<? extends MessageHandler>> messageHandlers = List.of(
                ChatMessageHandler.class,
                UserConnectedHandler.class,
                UserLoginHandler.class
        );

        Multibinder<MessageHandler> messageHandlerMultibinder = Multibinder.newSetBinder(binder(), MessageHandler.class);

        messageHandlers.forEach(messageHandlerClass -> messageHandlerMultibinder.addBinding().to(messageHandlerClass));
    }

    @Provides
    public Map<MessageType, MessageHandler> provideMessageHandlerMap(Set<MessageHandler> messageHandlers) {
        return messageHandlers.stream()
                .collect(
                        Collectors.toMap(
                                MessageHandler::getMessageType,
                                Function.identity()
                        ));
    }
}
