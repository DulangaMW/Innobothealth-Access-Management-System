package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.Claim;
import com.innobothealth.accessmanagementsystem.dto.ClaimDTO;
import com.innobothealth.accessmanagementsystem.service.ClaimService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
public class ClaimController {

    private final ClaimService claimService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }


    @PostMapping("create")
    public ResponseEntity<Claim> createClaim(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("memberId") ObjectId memberId,
            @RequestParam("dob") String date,
            @RequestParam("gender") String gender,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam("claimType") String claimType,
            @RequestParam("amount") int amount,
            @RequestParam("diagnosisId") ObjectId diagnosisId,
            @RequestParam("treatmentDate") String treatmentDate,
            @RequestParam("receipt") MultipartFile receipt) throws ParseException, IOException {

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
    public void deleteClaim(@RequestParam("email") String email) {
        claimService.deleteClaim(email);
    }

    @GetMapping("getAll")
    public List<Claim> getAllClaim() {
        return claimService.getAllClaims();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Claim> updateClaim(
            @PathVariable("id") String email,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("memberId") ObjectId memberId,
            @RequestParam("dob") String date,
            @RequestParam("gender") String gender,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("claimType") String claimType,
            @RequestParam("amount") int amount,
            @RequestParam("diagnosisId") ObjectId diagnosisId,
            @RequestParam("treatmentDate") String treatmentDate,
            @RequestParam("receipt") MultipartFile receipt) throws ParseException, IOException {

        ClaimDTO build = ClaimDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .memberId(memberId)
                .dob(dateFormat.parse(date))
                .gender(gender)
                .phoneNumber(phoneNumber)
                .claimType(claimType)
                .amount(amount)
                .diagnosisId(diagnosisId)
                .treatmentDate(dateFormat.parse(treatmentDate))
                .receipt(receipt.getBytes())
                .build();

        return ResponseEntity.status(201).body(claimService.updateClaim(email, build));

    }

    @PutMapping("approve/{email}")
    public void approveClaim(@PathVariable("email") String email) {
        claimService.approveClaim(email);
    }

}
