package com.mick.mchat;

import io.javalin.apibuilder.EndpointGroup;

/**
 * this might help, each controller can build its own endpoint group
 */
@FunctionalInterface
public interface MController {
    EndpointGroup getEndpointGroup();
}
