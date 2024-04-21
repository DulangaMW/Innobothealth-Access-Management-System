package com.innobothealth.accessmanagementsystem.repository;

import com.innobothealth.accessmanagementsystem.document.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends MongoRepository <Appointment, String>{

//    public List<Appointment> findByAppointment(String firstname);
    List<Appointment> findAppointmentByFirstname(String firstName);
}
