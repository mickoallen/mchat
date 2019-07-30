package com.mick.mchat.websocket.inbound;

import com.mick.mchat.websocket.outbound.OutboundMessage;
import io.javalin.websocket.WsContext;
import kotlin.jvm.functions.Function2;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class InboundMessageHandlerRegistry {
    private Map<InboundMessageType, Function2<InboundMessage, WsContext, Optional<? extends OutboundMessage>>> messageTypeFunctions;

    /**
     * lets see if i regret this later
     * */
    @Inject
    public InboundMessageHandlerRegistry(Set<InboundMessageHandler> inboundMessageHandlers) {
        validateMessageHandlers(inboundMessageHandlers);

        messageTypeFunctions = Map.ofEntries(inboundMessageHandlers.stream()
                .map(InboundMessageHandler::getHandlerMap)
                .map(Map::entrySet)
                .flatMap(Collection::stream).toArray(Map.Entry[]::new)
        );
    }

    /**
     * Validate there is only 1 handler per type.
     * */
    private void validateMessageHandlers(Set<InboundMessageHandler> inboundMessageHandlers) {
        Set<InboundMessageType> inboundMessageTypes = new HashSet<>();

        for (InboundMessageHandler inboundMessageHandler : inboundMessageHandlers) {
            Optional<InboundMessageType> duplicateInboundMessageType = inboundMessageHandler.getHandlerMap()
                    .keySet()
                    .stream()
                    .filter(inboundMessageTypes::contains)
                    .findAny();
            if(duplicateInboundMessageType.isPresent()){
                throw new IllegalArgumentException(InboundMessageType.class.getSimpleName() + " with name " + duplicateInboundMessageType.get().name() + " is declared multiple times in handlers." +
                        "There can only be 1 handler per type.");
            }
            inboundMessageTypes.addAll(inboundMessageHandler.getHandlerMap().keySet());
        }

    }

    public Function2<InboundMessage, WsContext, Optional<? extends OutboundMessage>> getMessageHandler(InboundMessageType inboundMessageType){
        return messageTypeFunctions.get(inboundMessageType);
    }
}
