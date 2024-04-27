package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.Patient;
import com.innobothealth.accessmanagementsystem.dto.PatientDTO;
import com.innobothealth.accessmanagementsystem.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("getAll")
    public List<Patient> getPatient() {
        return patientService.getAllPatient();
    }

    @PutMapping("update/{id}")
    public Patient update(@PathVariable String id, @RequestBody PatientDTO patientDTO) {
        return patientService.updatePatient(id, patientDTO);
    }

    @DeleteMapping("delete/{id}")
    public void update(@PathVariable String id) {
        patientService.deletePatient(id);
    }

}
