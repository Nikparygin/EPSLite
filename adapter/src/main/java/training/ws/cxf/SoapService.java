package training.ws.cxf;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public final class SoapService {

    private Tomcat tomcat;


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
            tomcat.getServer().await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
