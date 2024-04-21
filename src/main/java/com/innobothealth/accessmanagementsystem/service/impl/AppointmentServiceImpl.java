package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.Appointment;
import com.innobothealth.accessmanagementsystem.repository.AppointmentRepository;
import com.innobothealth.accessmanagementsystem.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j

public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Override
    public Appointment create(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findByAppointment(String firstname) {
        return appointmentRepository.findAppointmentByFirstname(firstname);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public void delete(String id) {
         appointmentRepository.deleteById(id);
    }
//id added in the pom.xml?
    @Override
    public Appointment update(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
}
