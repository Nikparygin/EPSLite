package ru.russianpost.epslite.adminbackend.controller;


import ru.russianpost.epslite.api.Client;
import ru.russianpost.epslite.exceptions.ClientErrorCode;
import ru.russianpost.epslite.exceptions.ClientException;
import ru.russianpost.epslite.adminbackend.dao.impl.JdbcTemplateClientDaoImpl;
import ru.russianpost.epslite.adminbackend.resources.ClientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

public class ClientController {

   private Logger logger = LoggerFactory.getLogger(ClientResource.class);

   private JdbcTemplateClientDaoImpl jdbcTemplateClientDao = new JdbcTemplateClientDaoImpl();

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

   public List<Client> getAllClients() {
      return jdbcTemplateClientDao.listClients();
   }

   public void updateClient(Client client) throws ClientException {
      checkClient(client);
      if (jdbcTemplateClientDao.updateClient(client) == 0) {
         throw new ClientException(ClientErrorCode.WRONG_CLIENT_VERSION);
      }
   }

   public void deleteClient(int id) {
      jdbcTemplateClientDao.removeClient(id);
   }

   public void addClient(Client client) throws ClientException {
      checkClient(client);
      try {
         jdbcTemplateClientDao.createClient(client);
      } catch (Exception e) {
         logger.error(e.getMessage());
         throw new ClientException(ClientErrorCode.ERROR_ADDING_CLIENT);
      }
   }
}
