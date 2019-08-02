package com.mick.mchat.websocket.inbound;

import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.outbound.OutMessage;
import com.mick.mchat.websocket.outbound.OutMessageType;
import com.mick.mchat.websocket.outbound.OutMessageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Scans all {@link InMessageHandler} classes for {@link MChatMessageHandler} annotated methods and registers them as lambdas
 * for the given {@link InMessageType}.
 */
public class InMessageHandlerRegistry {
    private static final Logger logger = LoggerFactory.getLogger(InMessageHandlerRegistry.class);

    private final Set<InMessageHandler> inMessageHandlers;

    private Map<InMessageType, BiFunction<InMessage, AuthenticationToken, OutMessageWrapper>> messageTypeFunctions = new HashMap<>();
    private Map<InMessageType, BiConsumer<InMessage, AuthenticationToken>> messageTypeConsumers = new HashMap<>();


    @Inject
    public InMessageHandlerRegistry(Set<InMessageHandler> inMessageHandlers) {
        this.inMessageHandlers = inMessageHandlers;
        registerMessageHandlers();
    }

    public Map<InMessageType, BiFunction<InMessage, AuthenticationToken, OutMessageWrapper>> getMessageTypeFunctions() {
        return messageTypeFunctions;
    }

    public Map<InMessageType, BiConsumer<InMessage, AuthenticationToken>> getMessageTypeConsumers() {
        return messageTypeConsumers;
    }

    private void registerMessageHandlers() {
        logger.info("Registering message handler methods...");

        inMessageHandlers.forEach(inboundMessageHandler -> {
            Method[] methods = inboundMessageHandler.getClass().getDeclaredMethods();
            for (Method method : methods) {
                MChatMessageHandler messageHandlerAnnotation = method.getAnnotation(MChatMessageHandler.class);
                if (messageHandlerAnnotation != null) {

                    validateMethodSignature(method);

                    //validate method declaration
                    boolean passAuthenticationToken = authenticationTokenRequired(method);

                    logger.info("{}.{} -> {}.{}", InMessageType.class.getSimpleName(), messageHandlerAnnotation.inType(), inboundMessageHandler.getClass().getSimpleName(), method.getName());

                    if (messageHandlerAnnotation.outType() == OutMessageType.NONE) {
                        messageTypeConsumers.put(messageHandlerAnnotation.inType(), (inboundMessage, authenticationToken) -> {
                                    try {
                                        if (passAuthenticationToken) {
                                            method.invoke(inboundMessageHandler, inboundMessage, authenticationToken);
                                        } else {
                                            method.invoke(inboundMessageHandler, inboundMessage);
                                        }
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        );
                    } else {
                        //function
                        messageTypeFunctions.put(messageHandlerAnnotation.inType(), (inboundMessage, authenticationToken) -> {
                            try {
                                OutMessage outMessage;
                                if (passAuthenticationToken) {
                                    outMessage = (OutMessage) method.invoke(inboundMessageHandler, inboundMessage, authenticationToken);
                                } else {
                                    outMessage = (OutMessage) method.invoke(inboundMessageHandler, inboundMessage);
                                }

                                return new OutMessageWrapper(messageHandlerAnnotation.outType()).body(outMessage);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                }

            }
        });
    }

    private void validateMethodSignature(Method method) {
        //validate input parameters
        Class<?>[] parameterTypes = method.getParameterTypes();

        if (!InMessage.class.isAssignableFrom(parameterTypes[0])) {
            throw new IllegalArgumentException("First parameter of method must be an InMessage - " + method.getDeclaringClass() + "." + method.getName());
        }

        if (parameterTypes.length == 2 && !AuthenticationToken.class.isAssignableFrom(parameterTypes[1])) {
            throw new IllegalArgumentException("Second parameter of method must be an AuthenticationToken - " + method.getDeclaringClass() + "." + method.getName());
        }
    }

    private boolean authenticationTokenRequired(Method method) {
        return method.getParameterCount() == 2;
    }
}
