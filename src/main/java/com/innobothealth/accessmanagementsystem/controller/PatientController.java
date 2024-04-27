package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.Patient;
import com.innobothealth.accessmanagementsystem.dto.PatientDTO;
import com.innobothealth.accessmanagementsystem.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("patient")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("create")
    public Patient createPatient(@RequestBody PatientDTO patientDTO) {
        return patientService.createpatient(patientDTO);
    }


}
