package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.Patient;
import com.innobothealth.accessmanagementsystem.dto.PatientDTO;
import com.innobothealth.accessmanagementsystem.repository.PatientRepository;
import com.innobothealth.accessmanagementsystem.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

    @Override
    public Patient updatePatient(String id, PatientDTO patientDTO) {
        Optional<Patient> byId = patientRepository.findById(id);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient not found");
        }

        Patient patient = byId.get();
        patient.setPatientName(patientDTO.getPatientName());
        patient.setAge(patientDTO.getAge());
        patient.setGender(patientDTO.getGender());
        patient.setAddress(patientDTO.getAddress());
        patient.setEmail(patientDTO.getEmail());
        patient.setDob(patientDTO.getDob());
        return patientRepository.save(patient);

    }

    @Override
    public void deletePatient(String id) {
        Optional<Patient> byId = patientRepository.findById(id);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient not found");
        }
        patientRepository.delete(byId.get());
    }
}
