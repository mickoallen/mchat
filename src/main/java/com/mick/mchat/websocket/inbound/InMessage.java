package com.mick.mchat.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Inbound messages interface.
 * All inbound websocket messages will be deserialized to this type.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface InMessage {
}
