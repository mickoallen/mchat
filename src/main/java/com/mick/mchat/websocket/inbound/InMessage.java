package com.mick.mchat.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Inbound messages.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface InMessage {
}
