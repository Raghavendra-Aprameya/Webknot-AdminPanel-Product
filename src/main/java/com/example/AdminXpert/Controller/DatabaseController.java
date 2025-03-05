package com.example.AdminXpert.Controller;


import com.example.AdminXpert.DTO.DbConnectDTO;
import com.example.AdminXpert.Services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/db")
public class DatabaseController {
    @Autowired
    DatabaseService databaseService;

    @PostMapping("/connect")
    public Map<String,String> connectToDatabase(@RequestBody DbConnectDTO connectionString)
    {
        return databaseService.connectAndInitializeDb(connectionString);
    }
}
