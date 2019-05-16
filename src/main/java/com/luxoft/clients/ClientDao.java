package com.luxoft.clients;

import javax.sql.DataSource;
import java.util.List;

/**
 * ClientDao interface produce all the functionality required for creating, updating, and removing Client objects
 */
public interface ClientDao {
    void setDataSource(DataSource dataSource);

    /**
     * Create new client
     * @param client - new client need to be created
     */
    void createClient(Client client);

    /**
     * Find client by id
     * @param id - client id using for searching
     * @return - client
     */
    Client getClientById(int id);

    /**
     * Find all clients
     * @return all clients in database
     */
    List listClients();

    /**
     * Remove client from database
     * @param id - client id that should be removed
     */
    void removeClient(int id);

    /**
     * Update client settings
     * @param client - new client settings
     */
    int updateClient(Client client);
}
