package com.innobothealth.accessmanagementsystem.repository;

import com.innobothealth.accessmanagementsystem.document.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    UserDetails findByEmail(String email);
    Boolean existsUserByEmail(String email);

}
