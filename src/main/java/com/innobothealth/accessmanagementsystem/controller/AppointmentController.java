package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.Appointment;
import com.innobothealth.accessmanagementsystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("appointment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @GetMapping("/all-appointments")
    public List<Appointment> getAllAppointments(){
        return appointmentService.findAll();
    }


    @PostMapping("/create")
    public Appointment create(@RequestBody Appointment appointment){
        appointment.setAppointmentId(UUID.randomUUID().toString());
        return appointmentService.create(appointment);


    }
    @PutMapping("/update/{id}")
    public Appointment update(@RequestBody Appointment appointment,@PathVariable String id){
        appointment.setAppointmentId(id);
        return appointmentService.update(appointment);


    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable String id){
         appointmentService.delete(id);
    }

    @PostMapping("/member/validate")
    public ResponseEntity<?> validate(@RequestParam String memberId) {
        // Validate member ID length
        if (memberId.length() != 24) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Member ID should be 24 characters long.");
        }
        if (!appointmentService.existsById(memberId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient with memberId " + memberId + " does not exist.");
        }
        return ResponseEntity.ok().build();

    }


}
