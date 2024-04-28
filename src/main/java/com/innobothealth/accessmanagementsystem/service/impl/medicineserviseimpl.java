package com.innobothealth.accessmanagementsystem.service.impl;
import com.innobothealth.accessmanagementsystem.document.MedicineEntity;
import com.innobothealth.accessmanagementsystem.dto.MedicineDto;
import com.innobothealth.accessmanagementsystem.dto.MedicineDtoSU;
import com.innobothealth.accessmanagementsystem.repository.medicinerepository;
import com.innobothealth.accessmanagementsystem.service.medicineservise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class medicineserviseimpl implements medicineservise {


    private final medicinerepository Mrepository;
    private final ModelMapper mapper;
    private final SequenceService sequenceService;
    private final PDFGenerator pdfGenerationService;


    @Override
    public void addmedicineservise(MedicineDto dto) {
        log.info("Received Serviseclassimpl to add medicine: {}", dto);
        MedicineEntity mappEntity = mapper.map(dto, MedicineEntity.class);
        // Generate the next sequence value
        mappEntity.setStockid(sequenceService.getNextSequence("stockid"));
        log.info("Convert model mapper Entity class: {}", dto);
        Mrepository.save(mappEntity);
    }

    @Override
    public List<MedicineDtoSU> AllmedicineShow() {
        List<MedicineEntity> medicineEntities = Mrepository.findAll();
        // Convert each MedicineEntity to MedicineDto
        return medicineEntities.stream()
                .map(entity -> mapper.map(entity, MedicineDtoSU.class))
                .collect(Collectors.toList());
    }

    @Override
    public MedicineDtoSU getMedicineName(String medicineName) {
        MedicineEntity medicineEntity = Mrepository.findByMedicineName(medicineName);
        return mapper.map(medicineEntity, MedicineDtoSU.class);
    }


    @Override
    public MedicineDto getMedicineId(String id) {
        MedicineEntity DEntity = Mrepository.findById(id).get();
        return mapper.map(DEntity, MedicineDto.class);
    }

    @Override
    public Optional<MedicineEntity> updateMedicine(MedicineDtoSU dto) {
        log.info("Received details to update: {}", dto);
        // Find the medicine entity by its name
        Optional<MedicineEntity> optionalEntity = Optional.ofNullable(Mrepository.findByMedicineName(dto.getMedicineName()));
        if (optionalEntity.isPresent()) {
            // If the entity exists, update its details
            MedicineEntity entityToUpdate = optionalEntity.get();
            entityToUpdate.setSupplier(dto.getSupplier());
            entityToUpdate.setMedicineType(dto.getMedicineType());
            entityToUpdate.setMedicineName(dto.getMedicineName());
            entityToUpdate.setExpireDate(dto.getExpireDate());
            entityToUpdate.setQuantity(dto.getQuantity());
            entityToUpdate.setUnitPrice(dto.getUnitPrice());

            // Save the updated entity
            MedicineEntity updatedEntity = Mrepository.save(entityToUpdate);
            log.info("Updated entity: {}", updatedEntity);
            return Optional.of(updatedEntity);
        } else {
            log.info("No medicine found with name: {}", dto.getMedicineName());
            return Optional.empty();
        }
    }

    @Override
    public String deleteExpireMedicine(String id) {
        log.info("Deleting expired medicine: {}", id);
        // Find the medicine entity by its name

        Optional<MedicineEntity> byId = Mrepository.findById(id);

        System.out.println("Stock Id====> " + id);
        System.out.println("medicineEntity====> " + byId.get());

        if (byId.isPresent()) {
            // If the entity exists, delete it
            Mrepository.delete(byId.get());
            log.info("Expired medicine deleted: {}", id);
            return "Expired medicine deleted: " + id;
        } else {
            log.info("No medicine found with name: {}", id);
            return "No medicine found with name: " + id;
        }
    }

    @Override
    public List<MedicineDtoSU> ExpireMedicineShow() {
        LocalDate currentDate = LocalDate.now();
        log.info("check date this sysstem: {}", currentDate);
        List<MedicineEntity> medicineEntities = Mrepository.findAll();
        log.info("check date this sysstem: {}", medicineEntities);
        // Filter out expired medicines
        List<MedicineDtoSU> validMedicines = medicineEntities.stream()
                .filter(entity -> entity.getExpireDate().compareTo(String.valueOf(currentDate)) <= 0) // Check expiration date
                .map(entity -> mapper.map(entity, MedicineDtoSU.class))
                .collect(Collectors.toList());
        return validMedicines;
    }
//    @Override
//    public boolean AllMedicinepdf() {
//        List<MedicineEntity> medicineEntities = Mrepository.findAll();
//        // Generate PDF
//        try {
//            pdfGenerationService.generatePDF("All Medicines", medicineEntities);
//            return true;
//        } catch (Exception e) {
//            log.error("Error generating PDF for all medicines: {}", e.getMessage());
//            return false;
//        }
//    }
//
//
//    @Override
//    public boolean ExpireMedicinepdf() {
//        List<MedicineDtoSU> dtoList = ExpireMedicineShow();
//        List<MedicineEntity> medicineEntities = dtoList.stream()
//                .map(dto -> mapper.map(dto, MedicineEntity.class))
//                .collect(Collectors.toList());
//
//        // Generate PDF
//        try {
//            pdfGenerationService.generatePDF("Expired Medicines", medicineEntities);
//            return true;
//        } catch (Exception e) {
//            log.error("Error generating PDF for expired medicines: {}", e.getMessage());
//            return false;
//        }
//    }

    @Override
    public byte[] AllMedicinepdf() {
        List<MedicineEntity> medicineEntities = Mrepository.findAll();
        // Generate PDF
        try {
            return pdfGenerationService.generatePDF("All Medicines", medicineEntities);
        } catch (Exception e) {
            log.error("Error generating PDF for all medicines: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public byte[] ExpireMedicinepdf() {
        List<MedicineDtoSU> dtoList = ExpireMedicineShow();
        List<MedicineEntity> medicineEntities = dtoList.stream()
                .map(dto -> mapper.map(dto, MedicineEntity.class))
                .collect(Collectors.toList());

        // Generate PDF
        try {
            return pdfGenerationService.generatePDF("Expired Medicines", medicineEntities);
        } catch (Exception e) {
            log.error("Error generating PDF for expired medicines: {}", e.getMessage());
            return null;
        }
    }


}

