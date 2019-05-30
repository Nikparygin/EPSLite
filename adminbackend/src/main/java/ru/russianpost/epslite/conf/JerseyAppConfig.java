package ru.russianpost.epslite.conf;

import ru.russianpost.epslite.adminbackend.resources.ClientResource;
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
