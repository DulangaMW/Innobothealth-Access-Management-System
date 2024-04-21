package com.innobothealth.accessmanagementsystem.repository;

import com.innobothealth.accessmanagementsystem.document.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {
}
