package com.luxoft.config;

import com.luxoft.resources.ClientResource;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Application config
 */
public class JerseyAppConfig extends ResourceConfig {

    public JerseyAppConfig() {
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        register(ClientResource.class);
    }
}
