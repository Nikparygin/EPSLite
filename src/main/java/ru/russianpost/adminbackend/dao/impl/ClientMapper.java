package ru.russianpost.adminbackend.dao.impl;

import ru.russianpost.api.ProcessClientSettings;
import ru.russianpost.api.ProcessSettings;
import org.springframework.jdbc.core.RowMapper;
import ru.russianpost.api.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper implementation that create Client.class() object from SQL Request result
 */
public class ClientMapper implements RowMapper {
    /**
     * Extract a value from request to Client.class() object
     *
     * @param rs     - Request result
     * @param rowNum - number of the current row
     * @return created client
     * @throws SQLException if a SQLException is encountered getting column values
     */
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("customer_id"));
        client.setShortName(rs.getString("short_name"));
        client.setDisplayName(rs.getString("display_name"));
        client.setInn(rs.getString("inn"));
        client.setEnable(rs.getBoolean("enabled"));
        client.setVersion(rs.getInt("version"));
//        client.setProcessClientSettings(new ProcessClientSettings(ProcessSettings.valueOf(rs.getString("allowed_delivery_type"))));
        try {
            client.setProcessClientSettings(new ProcessClientSettings(ProcessSettings.valueOf(rs.getString("allowed_delivery_type"))));
        } catch (Exception e) {
           throw new SQLException("", e);
        }
        return client;
    }
}
