package com.mick.mchat.websocket.inbound;

import com.mick.mchat.websocket.outbound.OutMessageType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MChatMessageHandler {
    InMessageType inType();
    OutMessageType outType() default OutMessageType.NONE;
}
