package training.ws.cxf.config;

import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.List;

public class Config {

   public List<String> getServerIpList() {
      return CONFIG.getStringList("cassandra.serverIp");
   }

   public String getKeyspace() {
      return CONFIG.getString("cassandra.keyspace");
   }

   private static Config instance;

   private Config() {

   }

   private static final File CONFIG_FILE = new File(
         "C:\\Dev\\!EPSLite\\EPSLite\\adapter\\src\\main\\resources\\conf\\soap.conf"
   );

   private final com.typesafe.config.Config CONFIG = ConfigFactory.parseFile(CONFIG_FILE).getConfig("conf");

   public static Config getInstance() {
      if (instance == null) {
         instance = new Config();
      }
      return instance;
   }
}
