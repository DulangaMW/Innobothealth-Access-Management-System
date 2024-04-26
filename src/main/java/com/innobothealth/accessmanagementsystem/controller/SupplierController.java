package com.innobothealth.accessmanagementsystem.controller;
import com.innobothealth.accessmanagementsystem.document.SupplierEntity;
import com.innobothealth.accessmanagementsystem.dto.SupplierDto;
import com.innobothealth.accessmanagementsystem.service.SupplierServise;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/supplier")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SupplierController {

    private final SupplierServise supplierServise;

    @PostMapping("/savesuplpier")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addSupplier(@RequestBody SupplierDto dto) {
        log.info("Received request to add medicine: {}", dto);
        try {
            supplierServise.addSupplierServise(dto);
            return ResponseEntity.ok("Data saved successfully");
        } catch (Exception e) {
            log.error("Failed to save medicine: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save medicine: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<SupplierDto> getAllSupplier() {
        return supplierServise.AllSupplierShow();
    }



    @GetMapping("/name/{SupplierName}")
    public ResponseEntity<SupplierDto> getSupplierByName(@PathVariable String companyName) {
        SupplierDto medicineDto = supplierServise.getSupplierName(companyName);
        if (medicineDto != null) {
            return new ResponseEntity<>(medicineDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public Optional<SupplierEntity> updateSuppler(@RequestBody SupplierDto dto){
        return supplierServise.updateSupplier(dto);
    }
    @DeleteMapping("/{companyName}")
    public String deleteSupplier(@PathVariable String companyName){
        return supplierServise.deleteSupplier(companyName);
    }

}
