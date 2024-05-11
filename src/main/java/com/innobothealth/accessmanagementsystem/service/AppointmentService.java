package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment create(Appointment appointment);

    List<Appointment> findByAppointment(String firstname);

    List<Appointment> findAll();

    void delete (String id);

    boolean existsById(String id);

    Appointment update(Appointment appointment);



}
