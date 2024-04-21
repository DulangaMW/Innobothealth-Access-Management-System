package com.innobothealth.accessmanagementsystem.repository;

import com.innobothealth.accessmanagementsystem.document.DoctorAvailability;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DoctorAvailabilityRepository extends MongoRepository<DoctorAvailability, String> {

    List<DoctorAvailability> findDoctorAvailabilitiesByAvailabilityFromIsBetween(LocalDateTime from, LocalDateTime to);
}
