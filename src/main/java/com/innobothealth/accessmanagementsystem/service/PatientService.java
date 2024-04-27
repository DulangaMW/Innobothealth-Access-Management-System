package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.Patient;
import com.innobothealth.accessmanagementsystem.dto.PatientDTO;

import java.util.List;

public interface PatientService {

    Patient createpatient(PatientDTO patientDTO);
    List<Patient> getAllPatient();
    Patient updatePatient(String id, PatientDTO patientDTO);
    void deletePatient(String id);

}
