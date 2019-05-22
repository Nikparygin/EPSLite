package ru.russianpost;

import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.russianpost.utils.Config;

import java.net.URI;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String BASE_URI = "http://127.0.0.1:8080";

    public static void main(String[] args) {
        try {
            final HttpServer server = startServer();
            server.getServerConfiguration().addHttpHandler(new CLStaticHttpHandler(Main.class.getClassLoader(), "swagger-ui/dist/"), "/dist");
            server.getServerConfiguration().addHttpHandler(new CLStaticHttpHandler(Main.class.getClassLoader(), "swagger-ui/public/"), "/swagger-ui");
            logger.info("SERVER STARTED");
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }

    private static HttpServer startServer() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setResourcePackage("ru.russianpost.adminbackend.resources");
        beanConfig.setScan(true);
        final ResourceConfig rc = new JerseyAppConfig().packages("ru.russianpost.adminbackend.resources");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
}