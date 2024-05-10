package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.Doctor;
import com.innobothealth.accessmanagementsystem.document.DoctorAvailability;
import com.innobothealth.accessmanagementsystem.dto.DoctorAndAvailability;
import com.innobothealth.accessmanagementsystem.repository.DoctorAvailabilityRepository;
import com.innobothealth.accessmanagementsystem.repository.DoctorRepository;
import com.innobothealth.accessmanagementsystem.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorAvailabilityRepository doctorAvailabilityRepository) {
        this.doctorRepository = doctorRepository;
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
    }

    @Override
    public Doctor create(Doctor doctor, String availabilityFrom, String availabilityTo) {
        Doctor save = doctorRepository.save(doctor);
        DoctorAvailability.DoctorAvailabilityBuilder availability = DoctorAvailability.builder()
                .doctorId(save.getId())
                .availabilityFrom(LocalDateTime.parse(availabilityFrom))
                .availabilityTo(LocalDateTime.parse(availabilityTo));

        doctorAvailabilityRepository.save(availability.build());
        return save;
    }

    @Override
    public List<DoctorAndAvailability> listDoctorAndAvailability(String date, String specialization) {

        ArrayList<DoctorAndAvailability> doctorAndAvailabilities = new ArrayList<>();

        String start = date.concat("T00:00:00");
        String end = date.concat("T23:59:59");

        List<DoctorAvailability> availabilities = doctorAvailabilityRepository.findDoctorAvailabilitiesByAvailabilityFromIsBetween(LocalDateTime.parse(start), LocalDateTime.parse(end));

        availabilities.stream().forEach(doctorAvailability -> {
            doctorRepository.findById(doctorAvailability.getDoctorId()).ifPresent(doctor -> {
                if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
                    doctorAndAvailabilities.add(DoctorAndAvailability.builder()
                            .id(doctor.getId())
                            .firstName(doctor.getFirstName())
                            .lastName(doctor.getLastName())
                            .specialization(doctor.getSpecialization())
                            .availabilityFrom(doctorAvailability.getAvailabilityFrom())
                            .availabilityTo(doctorAvailability.getAvailabilityTo())
                            .build()
                    );
                }
            });

        });

        return doctorAndAvailabilities;

    }

}
