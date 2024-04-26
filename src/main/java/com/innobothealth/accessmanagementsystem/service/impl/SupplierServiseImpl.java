package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.SupplierEntity;
import com.innobothealth.accessmanagementsystem.dto.SupplierDto;
import com.innobothealth.accessmanagementsystem.repository.SuppilerRepository;
import com.innobothealth.accessmanagementsystem.service.SupplierServise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequestMapping("supplier")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SupplierServiseImpl implements SupplierServise {

    private final SuppilerRepository Mrepository;
    private final ModelMapper mapper;



    @Override
    public void addSupplierServise(SupplierDto dto) {
        log.info("Received Serviseclassimpl to add medicine: {}", dto);
        SupplierEntity mappEntity = mapper.map(dto, SupplierEntity.class);
        // Generate the next sequence value
        log.info("Convert model mapper Entity class: {}", dto);
        Mrepository.save(mappEntity);
    }

    @Override
    public List<SupplierDto> AllSupplierShow() {
        List<SupplierEntity> medicineEntities = Mrepository.findAll();
        // Convert each MedicineEntity to MedicineDto
        return medicineEntities.stream()
                .map(entity -> mapper.map(entity, SupplierDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDto getSupplierName(String companyName) {
        SupplierEntity medicineEntity = Mrepository.findByCompanyName(companyName);
        return mapper.map(medicineEntity, SupplierDto.class);
    }

    @Override
    public Optional<SupplierEntity> updateSupplier(SupplierDto dto) {
        log.info("Received details to update: {}", dto);
        // Find the medicine entity by its name
        Optional<SupplierEntity> optionalEntity = Optional.ofNullable(Mrepository.findByCompanyName(dto.getCompanyName()));
        if (optionalEntity.isPresent()) {
            // If the entity exists, update its details
            SupplierEntity entityToUpdate = optionalEntity.get();
            entityToUpdate.setSupplier_name(dto.getSupplier_name());
            entityToUpdate.setCountry(dto.getCountry());
            entityToUpdate.setContact_person(dto.getContact_person());
            entityToUpdate.setPhone(dto.getPhone());

            // Save the updated entity
            SupplierEntity updatedEntity = Mrepository.save(entityToUpdate);
            log.info("Updated entity: {}", updatedEntity);
            return Optional.of(updatedEntity);
        } else {
            log.info("No medicine found with name: {}", dto.getCompanyName());
            return Optional.empty();
        }
    }

    @Override
    public String deleteSupplier(String companyName) {
        log.info("Deleting expired medicine: {}", companyName);
        // Find the medicine entity by its name
        SupplierEntity medicineEntity = Mrepository.findByCompanyName(companyName);
        if (medicineEntity != null) {
            // If the entity exists, delete it
            Mrepository.delete(medicineEntity);
            log.info("Expired medicine deleted: {}", companyName);
            return "Expired medicine deleted: " + companyName;
        } else {
            log.info("No medicine found with name: {}", companyName);
            return "No medicine found with name: " + companyName;
        }
    }
}
