package com.mick.mchat.websocket.inbound;

import com.mick.mchat.websocket.outbound.OutMessageType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MChatMessageHandler {
    InMessageType inType();
    OutMessageType outType() default OutMessageType.NONE;
}
