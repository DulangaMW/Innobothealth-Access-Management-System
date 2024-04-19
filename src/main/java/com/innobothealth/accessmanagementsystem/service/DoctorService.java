package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.Doctor;
import com.innobothealth.accessmanagementsystem.dto.DoctorAndAvailability;

import java.util.List;

public interface DoctorService {

    Doctor create(Doctor doctor, String availabilityFrom, String availabilityTo);
    List<DoctorAndAvailability> listDoctorAndAvailability(String date, String specialization);
}
