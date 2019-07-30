package com.mick.mchat.websocket.inbound;

/**
 * The default for jackson to fall back to, should never be used irl.
 */
public class NoMappingInboundMessage extends InboundMessage{
    public NoMappingInboundMessage() {
        super(InboundMessageType.NONE);
    }
}
