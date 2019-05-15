package com.luxoft;

import com.luxoft.config.JerseyAppConfig;

import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {

    private static final String BASE_URI = "http://127.0.0.1:8080";

    public static void main(String[] args) {
        final HttpServer server = startServer();
        server.getServerConfiguration().addHttpHandler(new CLStaticHttpHandler(Main.class.getClassLoader(), "swagger-ui/dist/"), "/dist");
        server.getServerConfiguration().addHttpHandler(new CLStaticHttpHandler(Main.class.getClassLoader(), "swagger-ui/public/"), "/swagger-ui");
    }

    private static HttpServer startServer() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setResourcePackage("com.luxoft.resources");
        beanConfig.setScan(true);
        final ResourceConfig rc = new JerseyAppConfig().packages("com.luxoft.resources");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
}