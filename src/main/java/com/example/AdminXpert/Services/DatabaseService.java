
package com.example.AdminXpert.Services;

import com.example.AdminXpert.DTO.DbConnectDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DatabaseService {

    private JdbcTemplate jdbcTemplate;

    // ✅ Method to establish a database connection using user-provided credentials
    public Map<String, String> connectAndInitializeDb(DbConnectDTO dbConnectDTO) {
        Map<String, String> response = new HashMap<>();

        // ✅ Get username, password, and connection URL from user input
        String username = dbConnectDTO.getUsername();
        String password = dbConnectDTO.getPassword();
        String connectionUrl = dbConnectDTO.getConnectionUrl();

        if (username == null || username.isEmpty() || password == null || password.isEmpty() ||
                connectionUrl == null || connectionUrl.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Username, password, and connection URL cannot be empty.");
            return response;
        }

        // ✅ Extract connection details (host, port, database, dbType)
        Map<String, String> connectionDetails = extractConnectionDetails(connectionUrl);
        String dbType = connectionDetails.get("dbType");
        String host = connectionDetails.get("host");
        String port = connectionDetails.get("port");
        String database = connectionDetails.get("database");

        if (dbType.isEmpty() || host.isEmpty() || database.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Invalid connection URL: Unable to extract required details.");
            return response;
        }

        try {
            // ✅ Construct JDBC URL based on the extracted database type
            String jdbcUrl;
            if ("postgresql".equalsIgnoreCase(dbType)) {
                jdbcUrl = "jdbc:postgresql://" + host + (port.isEmpty() ? "" : ":" + port) + "/" + database;
                if (connectionUrl.contains("?sslmode=require")) {
                    jdbcUrl += "?sslmode=require"; // Keep SSL if present in the original URL
                }
            } else if ("mysql".equalsIgnoreCase(dbType)) {
                jdbcUrl = "jdbc:mysql://" + host + (port.isEmpty() ? "" : ":" + port) + "/" + database;
                if (connectionUrl.contains("?ssl-mode=REQUIRED")) {
                    jdbcUrl += "?useSSL=true"; // MySQL requires useSSL=true for secure connections
                }
            } else {
                response.put("status", "error");
                response.put("message", "Unsupported database type: " + dbType);
                return response;
            }

            // ✅ Set up the data source and test connection
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl(jdbcUrl);
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            this.jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.execute("SELECT 1");

            response.put("status", "success");
            response.put("message", "Connected to the database successfully.");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Database connection error: " + e.getMessage());
        }

        return response;
    }

    public List<String> getTableNames() {
        if (this.jdbcTemplate == null) {
            throw new IllegalStateException("Database is not connected.");
        }

        String sqlQuery = "SHOW TABLES";
        try {
            return jdbcTemplate.queryForList(sqlQuery, String.class);
        } catch (Exception e) {
            sqlQuery = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'";
            return jdbcTemplate.queryForList(sqlQuery, String.class);
        }
    }


    public Map<String, Object> getColumnNames(String tableName) {
        Map<String, Object> response = new HashMap<>();

        if (jdbcTemplate == null) {
            response.put("status", "error");
            response.put("message", "Database is not connected.");
            return response;
        }


        try {
            String sql = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";
            List<String> columnNames = jdbcTemplate.queryForList(sql, new Object[]{tableName}, String.class);
            response.put("columns", columnNames);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error fetching columns: " + e.getMessage());
        }


        return response;
    }

    // ✅ Extracts database type, host, port, and database from connection URL (ignores username/password)
    private Map<String, String> extractConnectionDetails(String connectionUrl) {
        Map<String, String> details = new HashMap<>();

        // Regex to extract connection details (ignores username & password)
        Pattern pattern = Pattern.compile("^(\\w+)://(?:[^:@]+:[^@]+@)?([^:/]+)(?::(\\d+))?/([^?]+)");
        Matcher matcher = pattern.matcher(connectionUrl);

        if (matcher.find()) {
            details.put("dbType", matcher.group(1));   // Database type (mysql/postgresql)
            details.put("host", matcher.group(2));     // Host
            details.put("port", matcher.group(3) != null ? matcher.group(3) : ""); // Port (optional)
            details.put("database", matcher.group(4)); // Database name
        } else {
            details.put("dbType", "");
            details.put("host", "");
            details.put("port", "");
            details.put("database", "");
        }

        return details;
    }
}
