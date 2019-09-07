package com.mick.mchat.websocket.inbound;

import com.mick.mchat.security.AuthenticationService;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.MessageCodec;
import com.mick.mchat.websocket.outbound.OutMessageError;
import com.mick.mchat.websocket.outbound.OutMessageType;
import com.mick.mchat.websocket.outbound.OutMessageWrapper;
import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;

/**
 * Could use some in mem message broker instead. For this to scale to multiple nodes we'll need that.
 */
@Singleton
public class MessageDispatcher {
    private final static Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    private final InMessageHandlerRegistry inMessageHandlerRegistry;
    private final AuthenticationService authenticationService;

    @Inject
    public MessageDispatcher(
            final InMessageHandlerRegistry inMessageHandlerRegistry,
            final AuthenticationService authenticationService) {
        this.inMessageHandlerRegistry = inMessageHandlerRegistry;
        this.authenticationService = authenticationService;
    }

    public void dispatchMessage(String inboundMessageString, WsContext wsContext) {
        InMessageWrapper inMessageWrapper = getInboundMessageWrapper(inboundMessageString);
        InMessageType inMessageType = inMessageWrapper.getType();

        try {
            AuthenticationToken validAuthenticationToken = null;
            if (inMessageType.isAuthenticationRequired()) {
                validAuthenticationToken = authenticationService.getValidAuthenticationToken(inMessageWrapper.getAuthenticationToken());
            }

            InMessage inMessage = MessageCodec.deserialize(inboundMessageString, inMessageWrapper.getType().getClazz());

            if (inMessageHandlerRegistry.getMessageConsumers().containsKey(inMessageWrapper.getType())) {
                inMessageHandlerRegistry.getMessageConsumers()
                        .get(inMessageWrapper.getType())
                        .accept(inMessage, validAuthenticationToken, wsContext);
            } else if (inMessageHandlerRegistry.getReturningMessageConsumers().containsKey(inMessageWrapper.getType())) {
                OutMessageWrapper outMessageWrapper = inMessageHandlerRegistry.getReturningMessageConsumers()
                        .get(inMessageWrapper.getType())
                        .accept(inMessage, validAuthenticationToken, wsContext);

                wsContext.send(outMessageWrapper);
            }
        } catch (Exception e) {
            logger.error("Error!", e);
            wsContext.send(
                    new OutMessageWrapper(OutMessageType.ERROR)
                            .body(new OutMessageError()
                                    .setMessage(e.getCause().getMessage())
                                    .setTime(Instant.now().toEpochMilli())
                                    .setStatus(500))
            );
        }
    }

    private InMessageWrapper getInboundMessageWrapper(String inboundMessageString) {
        return MessageCodec.deserialize(inboundMessageString, InMessageWrapper.class);
    }
}
