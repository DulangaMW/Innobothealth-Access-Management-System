package com.innobothealth.accessmanagementsystem.repository;

import com.innobothealth.accessmanagementsystem.document.SupplierEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuppilerRepository extends MongoRepository<SupplierEntity,String> {
//    SupplierEntity findBySupplierName(String supplierName);


}
