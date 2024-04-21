package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.Appointment;
import com.innobothealth.accessmanagementsystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("appointment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @GetMapping("/allappointments")
    public List<Appointment> getAllAppointments(){
        return appointmentService.findAll();
    }
    @PostMapping("/create")
    public Appointment create(@RequestBody Appointment appointment){
        appointment.setAppointmentId(UUID.randomUUID().toString());
        return appointmentService.create(appointment);


    }
    @PutMapping("/update")
    public Appointment update(@RequestBody Appointment appointment){
        appointment.setAppointmentId(UUID.randomUUID().toString());
        return appointmentService.update(appointment);


    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable String id){
         appointmentService.delete(id);
    }


}
