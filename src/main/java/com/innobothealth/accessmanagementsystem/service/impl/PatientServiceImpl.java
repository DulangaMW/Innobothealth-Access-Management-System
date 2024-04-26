package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.Patient;
import com.innobothealth.accessmanagementsystem.dto.PatientDTO;
import com.innobothealth.accessmanagementsystem.repository.PatientRepository;
import com.innobothealth.accessmanagementsystem.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {


    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;

    public PatientServiceImpl(ModelMapper modelMapper, PatientRepository patientRepository) {
        this.modelMapper = modelMapper;
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient createpatient(PatientDTO patientDTO) {
        Patient map = modelMapper.map(patientDTO, Patient.class);
        return patientRepository.save(map);
    }
}
