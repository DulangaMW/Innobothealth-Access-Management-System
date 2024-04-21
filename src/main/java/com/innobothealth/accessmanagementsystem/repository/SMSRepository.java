package com.innobothealth.accessmanagementsystem.repository;

import com.innobothealth.accessmanagementsystem.document.SMSOTP;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSRepository extends MongoRepository<SMSOTP, String> {

    SMSOTP findByEmail(String email);
}
