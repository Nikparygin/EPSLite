package ru.russianpost.epslite.adapter;

import javax.xml.ws.Endpoint;

public class SMServicePublisher {

   private static SMServicePublisher instance;

   private SMServicePublisher() {

   }

   public static SMServicePublisher getInstance() {
      if (instance == null) {
         instance = new SMServicePublisher();
      }
      return instance;
   }

   public void publishSoapService() {
      Endpoint.publish("http://127.0.0.1:9994/adapter", new SMServiceImpl());
   }
}
