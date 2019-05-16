package com.luxoft.dbservice;

import com.luxoft.clients.Client;
import com.luxoft.clients.ClientDao;
import com.luxoft.clients.ClientMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * JdbcTemplateClientDaoImpl class implements all the functionality required for
 * creating, updating, and removing Client objects in database
 */
public class JdbcTemplateClientDaoImpl implements ClientDao {
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    /**
     * Applying connection settings
     * @param dataSource - connection settings described in dataSource bean in applicationContext.xml
     */
    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    /**
     * Create new client
     * @param client - new client need to be created
     */
    @Override
    public void createClient(Client client) throws Exception {
        String createSettingsRequest = "INSERT INTO admin_web_console.processing_settings (allowed_delivery_type) " +
              "VALUES (?)";
        jdbcTemplate.update(createSettingsRequest, client.getProcessClientSettings().getProcessSettings().toString());

        String findLastIdRequest = "SELECT id FROM admin_web_console.processing_settings ORDER BY \"id\" desc limit 1";
        int settingsId = (int) jdbcTemplate.queryForObject(findLastIdRequest, Integer.class);

        String createClientRequest = "INSERT INTO admin_web_console.customer (" +
                "customer_id, " +
                "processing_settings_id, " +
                "short_name, " +
                "display_name, " +
                "inn, " +
                "enabled" +
                ")" +
                "VALUES (?,?,?,?,?,?)";

        jdbcTemplate.update(createClientRequest,
                client.getId(),
                settingsId,
                client.getShortName(),
                client.getDisplayName(),
                client.getInn(),
                client.isEnable());
    }
    /**
     * Find client by id
     * @param id - client id using for searching
     * @return - client
     */
    @Override
    public Client getClientById(int id) {
        String getClientByIdRequest = "SELECT " +
                "customer.id, " +
                "customer_id, " +
                "short_name, " +
                "display_name, " +
                "inn, " +
                "enabled, " +
                "allowed_delivery_type, " +
                "customer.version " +
                "FROM admin_web_console.customer JOIN admin_web_console.processing_settings ON(" +
                "customer.processing_settings_id = processing_settings.id" +
                ") WHERE customer_id = ?";

        return (Client) jdbcTemplate.queryForObject(getClientByIdRequest, new Object[]{id}, new ClientMapper());
    }
    /**
     * Find all clients
     * @return all clients in database
     */
    @Override
    public List listClients() {
        String getAllClientsRequest = "SELECT " +
                "customer.id, " +
                "customer_id, " +
                "short_name, " +
                "display_name, " +
                "inn, " +
                "enabled, " +
                "allowed_delivery_type, " +
                "customer.version " +
                "FROM admin_web_console.customer JOIN admin_web_console.processing_settings ON(" +
                "customer.processing_settings_id = processing_settings.id)";

        return jdbcTemplate.query(getAllClientsRequest, new ClientMapper());
    }
    /**
     * Remove client from database
     * @param id - client id that should be removed
     */
    @Override
    public void removeClient(int id) {
        String deleteClientRequest = "DELETE FROM admin_web_console.customer WHERE customer_id = ?";
        jdbcTemplate.update(deleteClientRequest, id);
    }
    /**
     * Update client settings
     * @param client - new client settings
     */
    @Override
    public int updateClient( Client client) {
        String getSettingsIdRequest = "SELECT processing_settings_id " +
              "FROM admin_web_console.customer " +
              "WHERE customer_id = ?";
        int settingsId = (int) jdbcTemplate.queryForObject(
              getSettingsIdRequest,
              new Object[]{client.getId()},
              Integer.class
        );

        String updateSettingsRequest = "UPDATE admin_web_console.processing_settings " +
              "SET allowed_delivery_type = ? " +
              "WHERE id = ?";
        jdbcTemplate.update(
              updateSettingsRequest,
              client.getProcessClientSettings().getProcessSettings().toString(),
              settingsId
        );

        String updateClientRequest = "UPDATE admin_web_console.customer SET " +
                "customer_id = ?, " +
                "short_name = ?, " +
                "display_Name = ?, " +
                "inn = ?, " +
                "enabled = ?, " +
                "version = version + 1 " +
                "WHERE customer_id = ? and version = ?";

        int res = jdbcTemplate.update(
                updateClientRequest,
                client.getId(),
                client.getShortName(),
                client.getDisplayName(),
                client.getInn(),
                client.isEnable(),
                client.getId(),
                client.getVersion()
        );

        if (res == 0) {
            System.out.println("the differences between the versions detected "); // Добавить обработчик
        }
        return res;
    }
}
