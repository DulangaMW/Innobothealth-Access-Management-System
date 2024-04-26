package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.Patient;
import com.innobothealth.accessmanagementsystem.dto.PatientDTO;

public interface PatientService {

    Patient createpatient(PatientDTO patientDTO);

}
