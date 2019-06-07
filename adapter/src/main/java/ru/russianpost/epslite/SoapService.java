package ru.russianpost.epslite;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import ru.russianpost.epslite.cassandra.CassandraConnector;
import ru.russianpost.epslite.cassandra.RetryPolicyType;

import java.time.Duration;

public final class SoapService {

    private Tomcat tomcat;

    private static final ActorSystem epslite = ActorSystem.create("epslite");

    public static void main(final String[] args) {
        new SoapService().launch();
    }

    public void launch() {
        try {
            tomcat = new Tomcat();
            tomcat.setPort(8081);
            tomcat.getHost().setAutoDeploy(true);
            tomcat.getHost().setDeployOnStartup(true);
            tomcat.addWebapp(
                  "/letter_service",
                  "C:\\Dev\\!EPSLite\\EPSLite\\adapter\\src\\main\\webapp"
            );

            tomcat.start();

            initCassandra();

            ActorRef letterScaner = epslite.actorOf(Props.create(LetterScaner.class), "letterScanerActor");

            epslite.scheduler().schedule(
                  Duration.ZERO, Duration.ofSeconds(10), letterScaner, "scanStorage", epslite.dispatcher(), ActorRef.noSender()
            );

            tomcat.getServer().await();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initCassandra() {
        CassandraConnector.getInstance().init(
              Config.getInstance().getServerIpList().toArray(new String[0]),
              Config.getInstance().getKeyspace(),
              Config.getInstance().getDefaultConsistencyLevel(),
              (Integer) null,
              (RetryPolicyType) null
        );
    }

    private void stop() {
        try {
            tomcat.stop();
            tomcat.destroy();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
