package com.example.AdminXpert.Services;


import com.example.AdminXpert.Config.AesUtil;
import com.example.AdminXpert.DTO.SaveConnectionDTO;
import com.example.AdminXpert.Entity.ConnectionString;
import com.example.AdminXpert.Entity.Users;
import com.example.AdminXpert.Repository.ConnectionStringRepository;
import com.example.AdminXpert.Repository.UserRepository;
import com.example.AdminXpert.Services.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConnectionService {

    private final JWTService jwtService;
    private final UserRepository usersRepository;
    private final ConnectionStringRepository connectionStringRepository;

    public ConnectionService(JWTService jwtService, UserRepository usersRepository,
                             ConnectionStringRepository connectionStringRepository) {
        this.jwtService = jwtService;
        this.usersRepository = usersRepository;
        this.connectionStringRepository = connectionStringRepository;
    }

    public Map<String, String> saveDbString(SaveConnectionDTO request, HttpServletRequest httpRequest) {
        Map<String, String> response = new HashMap<>();

        try {
            // Extract JWT token from Authorization header
            String authHeader = httpRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.put("error", "Invalid or missing Authorization header");
                return response;
            }
            String token = authHeader.substring(7);

            // Extract username from JWT
            String username = jwtService.extractUsername(token);
            if (username == null) {
                response.put("error", "Invalid JWT token");
                return response;
            }

            // Fetch user from database
            Users user = usersRepository.findByUsername(username);
            if (user == null) {
                response.put("error", "User not found");
                return response;
            }

            // Encrypt the connection string using AES
            String encryptedConnectionString = AesUtil.encrypt(request.getConnectionString());

            // Save connection string to the database
            ConnectionString connectionString = new ConnectionString();
            connectionString.setUser(user);
            connectionString.setConnectionString(encryptedConnectionString);
            connectionStringRepository.save(connectionString);

            response.put("message", "Connection string saved successfully");
            return response;
        } catch (Exception e) {
            response.put("error", "An error occurred: " + e.getMessage());
            return response;
        }
    }
}
