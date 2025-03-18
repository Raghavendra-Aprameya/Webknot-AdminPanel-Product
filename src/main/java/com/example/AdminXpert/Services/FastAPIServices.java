//
//package com.example.AdminXpert.Services;
//
//import com.example.AdminXpert.DTO.GetUseCaseDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import com.example.AdminXpert.DTO.GetUseCasesDTO;
//import com.example.AdminXpert.DTO.UpdateRequestDTO;
//
//import java.util.Map;
//import java.util.List;
//
//@Service
//public class FastAPIServices {
//
//    private final String FASTAPI_BASE_URL = "http://localhost:8000/api/v1";
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public Map<String, Object> getUseCases() {
//        String url = FASTAPI_BASE_URL + "/use_cases";
//
//        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<Map<String, Object>>() {}
//        );
//
//        // Get the response body
//        Map<String, Object> responseBody = responseEntity.getBody();
//
//        // Return the whole response (including "useCases")
//        return responseBody != null ? responseBody : Map.of();
//    }
//
//
//    public ResponseEntity<Map<String, Object>> executeUseCase(GetUseCaseDTO request) {
//        String url = FASTAPI_BASE_URL + "/execute_use_case";
//        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
//        return ResponseEntity.ok(response.getBody());
//    }
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public ResponseEntity<Map<String, Object>> updateData(UpdateRequestDTO request) {
//        try {
//            System.out.println("Sending request to FastAPI: " + objectMapper.writeValueAsString(request));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        String url = FASTAPI_BASE_URL + "/update_data";
//        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
//        return ResponseEntity.ok(response.getBody());
//    }
//}
package com.example.AdminXpert.Services;

import com.example.AdminXpert.DTO.GetUseCaseDTO;
import com.example.AdminXpert.DTO.GetUseCasesDTO;
import com.example.AdminXpert.DTO.UpdateRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class FastAPIServices {

    private final String FASTAPI_BASE_URL = "http://localhost:8000/api/v1";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> getUseCases() {
        String url = FASTAPI_BASE_URL + "/use_cases";
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
            );
            return response.getBody() != null ? response.getBody() : Map.of();
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Failed to fetch use cases");
        }
    }

    public ResponseEntity<Map<String, Object>> executeUseCase(GetUseCaseDTO request) {
        String url = FASTAPI_BASE_URL + "/execute_use_case";
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Execution failed"));
        }
    }

    public ResponseEntity<Map<String, Object>> updateData(UpdateRequestDTO request) {
        String url = FASTAPI_BASE_URL + "/update_data";
        try {
            System.out.println("Sending request to FastAPI: " + objectMapper.writeValueAsString(request));
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Update failed"));
        }
    }
}
