package com.innobothealth.accessmanagementsystem.repository;
import com.innobothealth.accessmanagementsystem.document.MedicineEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface medicinerepository extends MongoRepository<MedicineEntity,String> {
    MedicineEntity findByMedicineName(String medicineName);


}
