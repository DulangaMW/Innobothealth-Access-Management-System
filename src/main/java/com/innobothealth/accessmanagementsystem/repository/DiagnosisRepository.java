package com.innobothealth.accessmanagementsystem.repository;

import com.innobothealth.accessmanagementsystem.document.Diagnosis;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRepository extends MongoRepository<Diagnosis, String> {
}
