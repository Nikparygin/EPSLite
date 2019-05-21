package ru.russianpost.utils;

import com.typesafe.config.ConfigFactory;

import java.io.File;

public class Config {
   private static final File CONFIG_FILE = new File(
         "C:\\Dev\\!EPSLite\\EPSLite\\src\\main\\resources\\conf\\postgresql.conf"
   );

   private Config() {

   }

   private static final com.typesafe.config.Config CONFIG = ConfigFactory.parseFile(CONFIG_FILE).getConfig("conf");

   public static String getURL() {
      return CONFIG.getString("properties.url");
   }

   public static String getUser() {
      return CONFIG.getString("properties.user");
   }

   public static String getPassword() {
      return CONFIG.getString("properties.password");
   }

}
