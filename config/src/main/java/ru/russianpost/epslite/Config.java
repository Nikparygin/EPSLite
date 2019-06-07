package ru.russianpost.epslite;

import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.List;

public class Config {

   public String getURL() {
      return CONFIG.getString("postgres.url");
   }

   public String getUser() {
      return CONFIG.getString("postgres.user");
   }

   public String getPassword() {
      return CONFIG.getString("postgres.password");
   }

   public List<String> getServerIpList() {
      return CONFIG.getStringList("cassandra.serverIp");
   }

   public String getKeyspace() {
      return CONFIG.getString("cassandra.keyspace");
   }

   public int getDefaultConsistencyLevel() {
      return CONFIG.getInt("cassandra.defaultConsistencyLevel");
   }

   private static Config instance;

   private Config() {

   }

   private static final File CONFIG_FILE = new File(
         "C:\\Dev\\!EPSLite\\EPSLite\\config\\src\\main\\resources\\config\\epslite.conf"
   );

   private final com.typesafe.config.Config CONFIG = ConfigFactory.parseFile(CONFIG_FILE).getConfig("conf");

   public static Config getInstance() {
      if (instance == null) {
         instance = new Config();
      }
      return instance;
   }
}
