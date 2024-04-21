package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.Doctor;
import com.innobothealth.accessmanagementsystem.dto.DoctorAndAvailability;
import com.innobothealth.accessmanagementsystem.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("doctor")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("create")
    public Doctor createDoctor(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("specialization") String specialization,
            @RequestParam("availabilityFrom") String availabilityFrom,
            @RequestParam("availabilityTo") String availabilityTo
    ) {

        Doctor build = Doctor.builder()
                .firstName(firstName)
                .lastName(lastName)
                .specialization(specialization)
                .build();
        return doctorService.create(build, availabilityFrom, availabilityTo);
    }

    @GetMapping("list")
    public List<DoctorAndAvailability> createDoctor(@RequestParam String date, @RequestParam String specialization) {
        return doctorService.listDoctorAndAvailability(date, specialization);
    }





}
