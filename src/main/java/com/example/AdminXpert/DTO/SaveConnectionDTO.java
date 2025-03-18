package com.example.AdminXpert.DTO;

import com.example.AdminXpert.Entity.ConnectionString;
import lombok.Data;

@Data
public class SaveConnectionDTO {
    private String ConnectionString;
    private String title;
}
