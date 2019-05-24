package ru.russianpost.epslite.conf;

import com.typesafe.config.ConfigFactory;

import java.io.File;

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

   private static final File CONFIG_FILE = new File(
         "C:\\Dev\\!EPSLite\\EPSLite\\src\\main\\resources\\conf\\epslite.conf"
   );

   private final com.typesafe.config.Config CONFIG = ConfigFactory.parseFile(CONFIG_FILE).getConfig("conf");

   private static Config instance;

   private Config() {

   }

   public static Config getInstance() {
      if (instance == null) {
         instance = new Config();
      }
      return instance;
   }

}
