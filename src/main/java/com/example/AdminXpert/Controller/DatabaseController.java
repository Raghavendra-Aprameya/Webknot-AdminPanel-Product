package com.example.AdminXpert.Controller;


import com.example.AdminXpert.DTO.DbConnectDTO;
import com.example.AdminXpert.Services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping("/api/v1/db")
public class DatabaseController {
    @Autowired
    DatabaseService databaseService;

    @PostMapping("/connect")
    public Map<String, String> connectToDatabase(@RequestBody DbConnectDTO connectionString) {
        return databaseService.connectAndInitializeDb(connectionString);
    }

    @GetMapping("/fetch-tables")
    public List<String> fetchTables() {
        return databaseService.getTableNames();
    }


    @GetMapping("/fetch-data/{tableName}")
    public Map<String, Object> getTableData(@PathVariable String tableName) {
        return databaseService.getTableData(tableName);
    }
}