
package com.example.AdminXpert.Services;

import com.example.AdminXpert.DTO.DbConnectDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DatabaseService {

    private JdbcTemplate jdbcTemplate;

    // ✅ Method to receive connection details and establish a connection
    public Map<String, String> connectAndInitializeDb(DbConnectDTO dbConnectDTO) {
        Map<String, String> response = new HashMap<>();

        // Extract username & password from connection string
        Map<String, String> credentials = extractCredentials(dbConnectDTO.getConnectionUrl());
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username.isEmpty() || password.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Invalid connection string: Unable to extract username and password.");
            return response;
        }

        // Set up DataSource
        try {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl("jdbc:" + dbConnectDTO.getConnectionUrl());
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            // Initialize JdbcTemplate for DB operations
            this.jdbcTemplate = new JdbcTemplate(dataSource);

            // ✅ Test connection
            jdbcTemplate.execute("SELECT 1");

            response.put("status", "success");
//            response.put("username", username);
//            response.put("password", password);
            response.put("message", "Connected to the database successfully.");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Database connection error: " + e.getMessage());
        }

        return response;
    }

    // ✅ Method to extract username & password from the connection string
    private Map<String, String> extractCredentials(String connectionUrl) {
        Map<String, String> credentials = new HashMap<>();

        // REGEX pattern for extracting credentials (Works for MySQL, PostgreSQL, MongoDB)
        Pattern pattern = Pattern.compile("//(.*?):(.*?)@");
        Matcher matcher = pattern.matcher(connectionUrl);

        if (matcher.find()) {
            credentials.put("username", matcher.group(1)); // Extract username
            credentials.put("password", matcher.group(2)); // Extract password
        } else {
            credentials.put("username", "");
            credentials.put("password", "");
        }

        return credentials;
    }
}
