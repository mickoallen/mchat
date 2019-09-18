package com.mick.mchat.websocket.inbound;

import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.outbound.OutMessageType;
import com.mick.mchat.websocket.outbound.OutMessageWrapper;
import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Scans all {@link InMessageHandler} classes for {@link MChatMessageHandler} annotated methods and registers them as lambdas
 * for the given {@link InMessageType}.
 *
 * A little bit of reflection never hurt anyone.
 */
public class InMessageHandlerRegistry {
    private static final Logger logger = LoggerFactory.getLogger(InMessageHandlerRegistry.class);

    private final Set<InMessageHandler> inMessageHandlers;

    private Map<InMessageType, ReturningMessageConsumer> returningMessageConsumers = new HashMap<>();
    private Map<InMessageType, MessageConsumer> messageConsumers = new HashMap<>();


    @Inject
    public InMessageHandlerRegistry(Set<InMessageHandler> inMessageHandlers) {
        this.inMessageHandlers = inMessageHandlers;
        registerMessageHandlers();
    }

    public Map<InMessageType, ReturningMessageConsumer> getReturningMessageConsumers() {
        return returningMessageConsumers;
    }

    public Map<InMessageType, MessageConsumer> getMessageConsumers() {
        return messageConsumers;
    }

    private void registerMessageHandlers() {
        logger.info("Registering message handler methods...");

        inMessageHandlers.forEach(inboundMessageHandler -> {
            Method[] methods = inboundMessageHandler.getClass().getDeclaredMethods();
            for (Method method : methods) {
                MChatMessageHandler messageHandlerAnnotation = method.getAnnotation(MChatMessageHandler.class);
                if (messageHandlerAnnotation != null) {

                    validateMethodSignature(method);

                    logger.info("{}.{} -> {}.{}", InMessageType.class.getSimpleName(), messageHandlerAnnotation.inType(), inboundMessageHandler.getClass().getSimpleName(), method.getName());

                    if (messageHandlerAnnotation.outType() == OutMessageType.NONE) {
                        messageConsumers.put(messageHandlerAnnotation.inType(), (inboundMessage, authenticationToken, wsContext) ->
                                method.invoke(inboundMessageHandler, buildParameters(method, inboundMessage, authenticationToken, wsContext))
                        );
                    } else {
                        returningMessageConsumers.put(messageHandlerAnnotation.inType(), (inboundMessage, authenticationToken, wsContext) ->
                                new OutMessageWrapper(messageHandlerAnnotation.outType())
                                        .body(
                                                method.invoke(inboundMessageHandler,
                                                        buildParameters(
                                                                method,
                                                                inboundMessage,
                                                                authenticationToken,
                                                                wsContext
                                                        )
                                                )
                                        ));
                    }
                }

            }
        });
    }

    private Object[] buildParameters(Method method, InMessage inboundMessage, AuthenticationToken authenticationToken, WsContext wsContext) {
        Object[] parameters = new Object[method.getParameterCount()];
        parameters[0] = inboundMessage;

        for(int i = 1; i < method.getParameterCount(); i++){
            if(AuthenticationToken.class.isAssignableFrom(method.getParameterTypes()[i])) {
                parameters[i] = authenticationToken;
            } else if(WsContext.class.isAssignableFrom(method.getParameterTypes()[i])){
                parameters[i] = wsContext;
            } else {
                throw new IllegalArgumentException("Unknown method parameter type: " + method.getParameterTypes()[i].getName());
            }
        }

        return parameters;
    }

    private void validateMethodSignature(Method method) {
        //validate input parameters
        Class<?>[] parameterTypes = method.getParameterTypes();

        if (!InMessage.class.isAssignableFrom(parameterTypes[0])) {
            throw new IllegalArgumentException("First parameter of method must be an InMessage - " + method.getDeclaringClass() + "." + method.getName());
        }
    }
}
