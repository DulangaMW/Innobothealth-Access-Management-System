package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.Appointment;
import com.innobothealth.accessmanagementsystem.repository.AppointmentRepository;
import com.innobothealth.accessmanagementsystem.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        Optional<Appointment> byId = appointmentRepository.findById(appointment.getAppointmentId());

        if(!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "APPOINTMENT does not exist");
        }
        Appointment appointment1 = byId.get();
        appointment1.setFirstname(appointment.getFirstname());
        appointment1.setLastname(appointment.getLastname());
        appointment1.setDate(appointment.getDate());
        appointment1.setDoctors_specialization(appointment.getDoctors_specialization());
        appointment1.setMail(appointment.getMail());
        appointment1.setMember_id(appointment.getMember_id());
        appointment1.setPhone_number(appointment.getPhone_number());
        appointment1.setSpecial_message(appointment.getSpecial_message());
        appointment1.setDoctor_id(appointment.getDoctor_id());

        return appointmentRepository.save(appointment1);
    }
}
