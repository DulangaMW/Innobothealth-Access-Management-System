package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.MedicineEntity;
import com.innobothealth.accessmanagementsystem.dto.MedicineDto;
import com.innobothealth.accessmanagementsystem.dto.MedicineDtoSU;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface medicineservise {

    void addmedicineservise(MedicineDto dto);
    List<MedicineDtoSU> AllmedicineShow();
    MedicineDtoSU getMedicineName(String medicineName);
    MedicineDto getMedicineId(String id);

    Optional<MedicineEntity> updateMedicine(MedicineDtoSU dto);

    String deleteExpireMedicine(String stockId);

    List<MedicineDtoSU> ExpireMedicineShow();

    File allMedicinePdf(String fileName);
    File ExpireMedicinepdf(String fileName);

}


