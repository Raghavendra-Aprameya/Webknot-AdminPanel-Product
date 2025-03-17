package com.example.AdminXpert.Services;

import com.example.AdminXpert.DTO.DbConnectDTO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Service
//public class DatabaseService {
//
//    private JdbcTemplate jdbcTemplate;
//
//    // establish a database connection
//    public Map<String, String> connectAndInitializeDb(DbConnectDTO dbConnectDTO) {
//        Map<String, String> response = new HashMap<>();
//
//        String username = dbConnectDTO.getUsername();
//        String password = dbConnectDTO.getPassword();
//        String connectionUrl = dbConnectDTO.getConnectionUrl();
//
//        if (username == null || username.isEmpty() || password == null || password.isEmpty() ||
//                connectionUrl == null || connectionUrl.isEmpty()) {
//            response.put("status", "error");
//            response.put("message", "Username, password, and connection URL cannot be empty.");
//            return response;
//        }
//
//        Map<String, String> connectionDetails = extractConnectionDetails(connectionUrl);
//        String dbType = connectionDetails.get("dbType");
//        String host = connectionDetails.get("host");
//        String port = connectionDetails.get("port");
//        String database = connectionDetails.get("database");
//
//        if (dbType.isEmpty() || host.isEmpty() || database.isEmpty()) {
//            response.put("status", "error");
//            response.put("message", "Invalid connection URL: Unable to extract required details.");
//            return response;
//        }
//
//        try {
//            String jdbcUrl;
//            if ("postgresql".equalsIgnoreCase(dbType)) {
//                jdbcUrl = "jdbc:postgresql://" + host + (port.isEmpty() ? "" : ":" + port) + "/" + database;
//            } else if ("mysql".equalsIgnoreCase(dbType)) {
//                jdbcUrl = "jdbc:mysql://" + host + (port.isEmpty() ? "" : ":" + port) + "/" + database;
//            } else {
//                response.put("status", "error");
//                response.put("message", "Unsupported database type: " + dbType);
//                return response;
//            }
//
//            DriverManagerDataSource dataSource = new DriverManagerDataSource();
//            dataSource.setUrl(jdbcUrl);
//            dataSource.setUsername(username);
//            dataSource.setPassword(password);
//            this.jdbcTemplate = new JdbcTemplate(dataSource);
//            jdbcTemplate.execute("SELECT 1");
//
//            sendConnectionDetailsToAPI(dbType, host, port, database, username, password, jdbcUrl);
//
//            response.put("status", "success");
//            response.put("message", "Connected to the database successfully.");
//        } catch (Exception e) {
//            response.put("status", "error");
//            response.put("message", "Database connection error: " + e.getMessage());
//        }
//
//        return response;
//    }
//
//    private void sendConnectionDetailsToAPI(String dbType, String host, String port, String database,
//                                            String username, String password, String jdbcUrl) {
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            String apiUrl = "http://localhost:8000/api/v1/dbDetails";
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            Map<String, String> requestBody = new HashMap<>();
//            requestBody.put("DB_TYPE", dbType);
//            requestBody.put("DB_HOST", host);
//            requestBody.put("DB_PORT", port);
//            requestBody.put("DB_NAME", database);
//            requestBody.put("DB_USER", username);
//            requestBody.put("DB_PASSWORD", password);
//            requestBody.put("JDBC_URL", jdbcUrl);
//
//            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
//            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
//            System.out.println(stringResponseEntity);
//        } catch (Exception e) {
//            System.err.println("Error sending DB details to API: " + e.getMessage());
//        }
//    }
//
//    private Map<String, String> extractConnectionDetails(String connectionUrl) {
//        Map<String, String> details = new HashMap<>();
//        Pattern pattern = Pattern.compile("^(\\w+)://(?:[^:@]+:[^@]+@)?([^:/]+)(?::(\\d+))?/([^?]+)");
//        Matcher matcher = pattern.matcher(connectionUrl);
//        if (matcher.find()) {
//            details.put("dbType", matcher.group(1));
//            details.put("host", matcher.group(2));
//            details.put("port", matcher.group(3) != null ? matcher.group(3) : "");
//            details.put("database", matcher.group(4));
//        }
//        return details;
//    }
//}
//
//
//
//    public List<String> getTableNames() {
//        if (this.jdbcTemplate == null) {
//            throw new IllegalStateException("Database is not connected.");
//        }
//
//        String sqlQuery = "SHOW TABLES";
//        try {
//            return jdbcTemplate.queryForList(sqlQuery, String.class);
//        } catch (Exception e) {
//            sqlQuery = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'";
//            return jdbcTemplate.queryForList(sqlQuery, String.class);
//        }
//    }
//
//
//    public Map<String, Object> getTableData(String tableName) {
//        Map<String, Object> response = new HashMap<>();
//
//        if (jdbcTemplate == null) {
//            response.put("status", "error");
//            response.put("message", "Database is not connected.");
//            return response;
//        }
//
//        try {
//            //fetch column names
//            String columnSql = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";
//            List<String> columnNames = jdbcTemplate.queryForList(columnSql, new Object[]{tableName}, String.class);
//
//            //fetch rows
//            String dataSql = "SELECT * FROM " + tableName;
//            List<Map<String, Object>> rows = jdbcTemplate.queryForList(dataSql);
//            response.put("columns", columnNames);
//            response.put("rows", rows);
//        } catch (Exception e) {
//            response.put("status", "error");
//            response.put("message", "Error fetching data: " + e.getMessage());
//        }
//
//        return response;
//    }
//
//
//    // Extracts database type, host, port, and database from connection URL
//    private Map<String, String> extractConnectionDetails(String connectionUrl) {
//        Map<String, String> details = new HashMap<>();
//
//        // Regex to extract connection details
//        Pattern pattern = Pattern.compile("^(\\w+)://(?:[^:@]+:[^@]+@)?([^:/]+)(?::(\\d+))?/([^?]+)");
//        Matcher matcher = pattern.matcher(connectionUrl);
//
//        if (matcher.find()) {
//            details.put("dbType", matcher.group(1));
//            details.put("host", matcher.group(2));
//            details.put("port", matcher.group(3) != null ? matcher.group(3) : "");
//            details.put("database", matcher.group(4));
//        } else {
//            details.put("dbType", "");
//            details.put("host", "");
//            details.put("port", "");
//            details.put("database", "");
//        }
//
//        return details;
//    }
//}




import com.example.AdminXpert.DTO.DbConnectDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DatabaseService {

    private JdbcTemplate jdbcTemplate;

    public Map<String, String> connectAndInitializeDb(DbConnectDTO dbConnectDTO) {
        Map<String, String> response = new HashMap<>();

        String username = dbConnectDTO.getUsername();
        String password = dbConnectDTO.getPassword();
        String connectionUrl = dbConnectDTO.getConnectionUrl();

        if (username == null || username.isEmpty() || password == null || password.isEmpty() ||
                connectionUrl == null || connectionUrl.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Username, password, and connection URL cannot be empty.");
            return response;
        }

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
            String jdbcUrl;
            if ("postgresql".equalsIgnoreCase(dbType)) {
                jdbcUrl = "jdbc:postgresql://" + host + (port.isEmpty() ? "" : ":" + port) + "/" + database;
            } else if ("mysql".equalsIgnoreCase(dbType)) {
                jdbcUrl = "jdbc:mysql://" + host + (port.isEmpty() ? "" : ":" + port) + "/" + database;
            } else {
                response.put("status", "error");
                response.put("message", "Unsupported database type: " + dbType);
                return response;
            }

            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl(jdbcUrl);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            this.jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.execute("SELECT 1");

            sendConnectionDetailsToAPI(dbType, host, port, database, username, password, jdbcUrl);

            response.put("status", "success");
            response.put("message", "Connected to the database successfully.");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Database connection error: " + e.getMessage());
        }

        return response;
    }

    private void sendConnectionDetailsToAPI(String dbType, String host, String port, String database,
                                            String username, String password, String jdbcUrl) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "http://localhost:8000/api/v1/dbDetails";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("DB_TYPE", dbType);
            requestBody.put("DB_HOST", host);
            requestBody.put("DB_PORT", port);
            requestBody.put("DB_NAME", database);
            requestBody.put("DB_USER", username);
            requestBody.put("DB_PASSWORD", password);
            requestBody.put("JDBC_URL", jdbcUrl);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
            System.out.println(stringResponseEntity);
        } catch (Exception e) {
            System.err.println("Error sending DB details to API: " + e.getMessage());
        }
    }

    private Map<String, String> extractConnectionDetails(String connectionUrl) {
        Map<String, String> details = new HashMap<>();
        Pattern pattern = Pattern.compile("^(\\w+)://(?:[^:@]+:[^@]+@)?([^:/]+)(?::(\\d+))?/([^?]+)");
        Matcher matcher = pattern.matcher(connectionUrl);
        if (matcher.find()) {
            details.put("dbType", matcher.group(1));
            details.put("host", matcher.group(2));
            details.put("port", matcher.group(3) != null ? matcher.group(3) : "");
            details.put("database", matcher.group(4));
        }
        return details;
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


    public Map<String, Object> getTableData(String tableName) {
        Map<String, Object> response = new HashMap<>();

        if (jdbcTemplate == null) {
            response.put("status", "error");
            response.put("message", "Database is not connected.");
            return response;
        }

        try {
            //fetch column names
            String columnSql = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";
            List<String> columnNames = jdbcTemplate.queryForList(columnSql, new Object[]{tableName}, String.class);

            //fetch rows
            String dataSql = "SELECT * FROM " + tableName;
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(dataSql);
            response.put("columns", columnNames);
            response.put("rows", rows);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error fetching data: " + e.getMessage());
        }

        return response;
    }
}
