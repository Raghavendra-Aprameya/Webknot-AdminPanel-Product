//package com.example.AdminXpert.Controller;
//
//import com.example.AdminXpert.DTO.GetUseCasesDTO;
//import com.example.AdminXpert.Services.FastAPIServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/v1/fastapi")
//public class FastAPIController {
//
//    @Autowired
//    FastAPIServices fastAPIServices;
//
//
//    @GetMapping("/use-cases")
//    public ResponseEntity<GetUseCasesDTO> getUseCases()
//    {
//        return  fastAPIServices.getUseCases();
//    }
//}
package com.example.AdminXpert.Controller;

import com.example.AdminXpert.DTO.GetUseCaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.AdminXpert.DTO.GetUseCasesDTO;
import com.example.AdminXpert.DTO.UpdateRequestDTO;
import com.example.AdminXpert.Services.FastAPIServices;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/fastapi")
public class FastAPIController {

    @Autowired
    private FastAPIServices fastAPIServices;

    @GetMapping("/use-cases")
    public ResponseEntity<Map<String, Object>> getUseCases() {
        return ResponseEntity.ok(fastAPIServices.getUseCases());
    }

    @PostMapping("/execute_use_case")
    public ResponseEntity<Map<String, Object>> executeUseCase(@RequestBody GetUseCaseDTO request) {
        return fastAPIServices.executeUseCase(request);
    }

    @PostMapping("/update_data")
    public ResponseEntity<Map<String, Object>> updateData(@RequestBody UpdateRequestDTO request) {
        return fastAPIServices.updateData(request);
    }
}
