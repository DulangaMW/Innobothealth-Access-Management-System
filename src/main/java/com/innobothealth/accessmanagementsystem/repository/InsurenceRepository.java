package com.innobothealth.accessmanagementsystem.repository;


import com.innobothealth.accessmanagementsystem.document.Insurence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsurenceRepository extends MongoRepository<Insurence, String> {
}
