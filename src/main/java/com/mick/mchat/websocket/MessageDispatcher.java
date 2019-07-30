package com.mick.mchat.websocket;

import com.mick.mchat.websocket.inbound.InboundMessage;
import com.mick.mchat.websocket.inbound.InboundMessageHandlerRegistry;
import com.mick.mchat.websocket.outbound.OutboundMessage;
import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * Could use some in mem message broker instead. For this to scale to multiple nodes we'll need that.
 */
@Singleton
public class MessageDispatcher {
    private final static Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    private final InboundMessageHandlerRegistry inboundMessageHandlerRegistry;

    @Inject
    public MessageDispatcher(final InboundMessageHandlerRegistry inboundMessageHandlerRegistry) {
        this.inboundMessageHandlerRegistry = inboundMessageHandlerRegistry;
    }

    public void dispatchMessage(InboundMessage inboundMessage, WsContext wsContext) {
        Optional<? extends OutboundMessage> outboundMessageOptional;

        try {
            outboundMessageOptional = inboundMessageHandlerRegistry
                    .getMessageHandler(inboundMessage.getType())
                    .invoke(inboundMessage, wsContext);
        } catch (Exception e) {
            logger.error("Something went wrong invoking message handler for message: {}", inboundMessage, e);
            return;
        }

        if(outboundMessageOptional.isEmpty()){
            return;
        }

        wsContext.send(MessageCodec.serialize(outboundMessageOptional.get()));
    }
}
