package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.MedicineEntity;
import com.innobothealth.accessmanagementsystem.document.SupplierEntity;
import com.innobothealth.accessmanagementsystem.dto.MedicineDto;
import com.innobothealth.accessmanagementsystem.dto.MedicineDtoSU;
import com.innobothealth.accessmanagementsystem.dto.SupplierDto;

import java.util.List;

import java.util.Optional;

public interface SupplierServise {
    void addSupplierServise(SupplierDto dto);
    List<SupplierDto> AllSupplierShow();
    SupplierDto getSupplierName(String companyName);
    Optional<SupplierEntity> updateSupplier(SupplierDto dto);
    String deleteSupplier(String companyName);
}
