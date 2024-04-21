package com.innobothealth.accessmanagementsystem.controller;


import com.innobothealth.accessmanagementsystem.document.MedicineEntity;
import com.innobothealth.accessmanagementsystem.dto.MedicineDto;
import com.innobothealth.accessmanagementsystem.dto.MedicineDtoSU;
import com.innobothealth.accessmanagementsystem.service.medicineservise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/medicine")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class medicinecontroller {

    private final medicineservise MedicineServise;


    @PostMapping("/saveMedi")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addmedicine(@RequestBody MedicineDto dto) {
        log.info("Received request to add medicine: {}", dto);
        try {
            MedicineServise.addmedicineservise(dto);
            return ResponseEntity.ok("Data saved successfully");
        } catch (Exception e) {
            log.error("Failed to save medicine: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save medicine: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<MedicineDtoSU> getAllMedicine() {
        return MedicineServise.AllmedicineShow();
    }

    @GetMapping("/{id}")
    public MedicineDto getMedicineId(@PathVariable Long id){
        return MedicineServise.getMedicineId(id);
    }

    @GetMapping("/name/{medicineName}")
    public ResponseEntity<MedicineDtoSU> getMedicineByName(@PathVariable String medicineName) {
        MedicineDtoSU medicineDto = MedicineServise.getMedicineName(medicineName);
        if (medicineDto != null) {
            return new ResponseEntity<>(medicineDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public Optional<MedicineEntity> updateMedicine(@RequestBody MedicineDtoSU dto){
        return MedicineServise.updateMedicine(dto);
    }
    @DeleteMapping("/{medicineName}")
    public String deleteExpireMedicine(@PathVariable String medicineName){
        return MedicineServise.deleteExpireMedicine(medicineName);
    }

    @GetMapping("/ex")
    public List<MedicineDtoSU> getAllExpireMedicine() {
        return MedicineServise.ExpireMedicineShow();
    }

//    @GetMapping("/generate-pdf/all")
//    public boolean generateAllMedicinePdf() {
//
//        return MedicineServise.AllMedicinepdf();
//    }

    @GetMapping("/generate-pdf/all")
    public ResponseEntity<byte[]> generateAllMedicinePdf() {
        try {
            byte[] pdfContent = MedicineServise.AllMedicinepdf(); // Call service method to generate PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "all_medicine.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            log.error("Failed to generate all medicine PDF: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/generate-pdf/expired")
    public ResponseEntity<byte[]> generateExpiredMedicinePdf() {
        try {
            byte[] pdfContent = MedicineServise.ExpireMedicinepdf(); // Call service method to generate PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "expired_medicine.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            log.error("Failed to generate expired medicine PDF: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

