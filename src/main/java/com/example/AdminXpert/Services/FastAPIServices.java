package com.example.AdminXpert.Services;

import com.example.AdminXpert.DTO.GetUseCasesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FastAPIServices {

    private final String FASTAPI_URL = "http://localhost:8000/api/v1/use_cases/";

    public ResponseEntity<GetUseCasesDTO> getUseCases() {
        RestTemplate restTemplate = new RestTemplate();

        // Make API call to FastAPI
        ResponseEntity<GetUseCasesDTO> response = restTemplate.getForEntity(FASTAPI_URL, GetUseCasesDTO.class);

        // Return response from FastAPI to frontend
        return ResponseEntity.ok(response.getBody());
    }
}
