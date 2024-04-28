package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.Insurence;
import com.innobothealth.accessmanagementsystem.document.User;
import com.innobothealth.accessmanagementsystem.dto.InsuranceDTO;
import com.innobothealth.accessmanagementsystem.dto.UserDTO;
import com.innobothealth.accessmanagementsystem.service.InsuranceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("insurance")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InsuranceController {

    private final InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @PostMapping("create")
    public Insurence registerInsurance(@RequestBody @Validated InsuranceDTO insuranceDTO) {
        return insuranceService.createInsurence(insuranceDTO);
    }

    @PutMapping("update/{id}")
    public Insurence updateInsurance(@RequestBody @Validated InsuranceDTO insuranceDTO, @PathVariable String id) {
        return insuranceService.updateInsurance(insuranceDTO, id);
    }

    @DeleteMapping("delete/{id}")
    public void deleteInsurance(@PathVariable String id) {
        insuranceService.deleteInsurance(id);
    }

    @GetMapping("getAll")
    public List<Insurence> getAllInsurance() {
        return insuranceService.getAllInsurances();
    }

}
