package com.mick.mchat;

import io.javalin.apibuilder.EndpointGroup;

@FunctionalInterface
public interface MController {
    EndpointGroup getEndpointGroup();
}
