package com.innobothealth.accessmanagementsystem.repository;
import com.innobothealth.accessmanagementsystem.document.MedicineEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface medicinerepository extends MongoRepository<MedicineEntity,Long> {
    MedicineEntity findByMedicineName(String medicineName);


}