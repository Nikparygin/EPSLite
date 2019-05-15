package com.luxoft;

import com.luxoft.clients.Client;
import com.luxoft.clients.exceptions.ClientErrorCode;
import com.luxoft.clients.exceptions.ClientException;
import com.luxoft.dbservice.JdbcTemplateClientDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class RequestController {

   private ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

   private JdbcTemplateClientDaoImpl jdbcTemplateClientDao = (JdbcTemplateClientDaoImpl) context.getBean("jdbcTemplateClientDao");

   private void checkClient(Client client) throws ClientException {
      if (client == null) {
         throw new ClientException(ClientErrorCode.NULL_CLIENT);
      }
      if (client.getDisplayName() == null || client.getDisplayName().isEmpty()) {
         throw new ClientException(ClientErrorCode.WRONG_DISPLAYNAME);
      }
      if (client.getShortName() == null || client.getShortName().isEmpty()) {
         throw new ClientException(ClientErrorCode.WRONG_SHORTNAME);
      }
      if (!client.getInn().matches("[0-9]{10}")) {
         throw new ClientException(ClientErrorCode.WRONG_INN);
      }
      if (client.getProcessClientSettings() == null) {
         throw new ClientException(ClientErrorCode.WRONG_PROCESS_CLIENT_SETTINGS);
      }
   }

   public Client getClientById(int id) {
      return jdbcTemplateClientDao.getClientById(id);
   }

   public List getAllClients() {
      return jdbcTemplateClientDao.listClients();
   }

   public void updateClient(Client client) throws ClientException {
      checkClient(client);
      jdbcTemplateClientDao.updateClient(client);
   }

   public void deleteClient(int id) {
      jdbcTemplateClientDao.removeClient(id);
   }

   public void addClient(Client client) throws ClientException {
      checkClient(client);
      jdbcTemplateClientDao.createClient(client);
   }
}
