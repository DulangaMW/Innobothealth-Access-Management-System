package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.Claim;
import com.innobothealth.accessmanagementsystem.dto.ClaimDTO;
import com.innobothealth.accessmanagementsystem.service.ClaimService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("claim")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClaimController {

    private final ClaimService claimService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }


    @PostMapping("create")
    public ResponseEntity<Claim> createClaim(
            @RequestParam("firstName") @NotNull String firstName,
            @RequestParam("lastName") @NotNull String lastName,
            @RequestParam("memberId") @NotNull String memberId,
            @RequestParam("dob") @NotNull String date,
            @RequestParam("gender") @NotNull String gender,
            @RequestParam("phoneNumber") @NotNull String phoneNumber,
            @RequestParam("email") @NotNull String email,
            @RequestParam("claimType") @NotNull String claimType,
            @RequestParam("amount") @NotNull int amount,
            @RequestParam("diagnosisId") @NotNull String diagnosisId,
            @RequestParam("treatmentDate") @NotNull String treatmentDate,
            @RequestParam("receipt") @NotNull MultipartFile receipt) throws ParseException, IOException {

        ClaimDTO build = ClaimDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .memberId(memberId)
                .dob(dateFormat.parse(date))
                .gender(gender)
                .phoneNumber(phoneNumber)
                .email(email)
                .claimType(claimType)
                .amount(amount)
                .diagnosisId(diagnosisId)
                .treatmentDate(dateFormat.parse(treatmentDate))
                .receipt(receipt.getBytes())
                .build();

        return ResponseEntity.status(201).body(claimService.createClaim(build));

    }

    @DeleteMapping("delete")
    public void deleteClaim(@RequestParam("id") @NotNull String id) {
        claimService.deleteClaim(id);
    }

    @GetMapping("getAll")
    public List<Claim> getAllClaim() {
        return claimService.getAllClaims();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Claim> updateClaim(
            @PathVariable("id") @NotNull String id,
            @RequestParam("firstName") @NotNull String firstName,
            @RequestParam("lastName") @NotNull String lastName,
            @RequestParam("memberId") @NotNull String memberId,
            @RequestParam("email") @NotNull String email,
            @RequestParam("phoneNumber") @NotNull String phoneNumber,
            @RequestParam("amount") @NotNull int amount,
            @RequestParam("treatmentDate") @NotNull String treatmentDate,
            @RequestParam("receipt") MultipartFile receipt) throws ParseException, IOException {

        ClaimDTO build = ClaimDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .memberId(memberId)
                .email(email)
                .phoneNumber(phoneNumber)
                .amount(amount)
                .treatmentDate(dateFormat.parse(treatmentDate))
                .receipt(receipt.getBytes())
                .build();

        return ResponseEntity.status(201).body(claimService.updateClaim(id, build));

    }

    @PutMapping("approve/{id}")
    public void approveClaim(@PathVariable("id") @NotNull String id) {
        claimService.approveClaim(id);
    }

}
