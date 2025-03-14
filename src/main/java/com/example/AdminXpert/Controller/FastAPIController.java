package com.example.AdminXpert.Controller;

import com.example.AdminXpert.DTO.GetUseCasesDTO;
import com.example.AdminXpert.Services.FastAPIServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/fastapi")
public class FastAPIController {

    @Autowired
    FastAPIServices fastAPIServices;


    @GetMapping("/use-cases")
    public ResponseEntity<GetUseCasesDTO> getUseCases()
    {
        return  fastAPIServices.getUseCases();
    }
}
