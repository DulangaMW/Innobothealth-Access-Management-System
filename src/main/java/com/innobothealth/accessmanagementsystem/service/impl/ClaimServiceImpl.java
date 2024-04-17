package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.Claim;
import com.innobothealth.accessmanagementsystem.document.Diagnosis;
import com.innobothealth.accessmanagementsystem.document.Insurence;
import com.innobothealth.accessmanagementsystem.dto.ClaimDTO;
import com.innobothealth.accessmanagementsystem.repository.ClaimRepository;
import com.innobothealth.accessmanagementsystem.repository.DiagnosisRepository;
import com.innobothealth.accessmanagementsystem.repository.InsurenceRepository;
import com.innobothealth.accessmanagementsystem.service.ClaimService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClaimServiceImpl implements ClaimService {

    private final ModelMapper modelMapper;
    private final InsurenceRepository insurenceRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final ClaimRepository claimRepository;

    public ClaimServiceImpl(ModelMapper modelMapper, InsurenceRepository insurenceRepository, DiagnosisRepository diagnosisRepository, ClaimRepository claimRepository) {
        this.modelMapper = modelMapper;
        this.insurenceRepository = insurenceRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.claimRepository = claimRepository;
    }

    @Override
    public Claim createClaim(ClaimDTO claim) {

        Optional<Insurence> byId = insurenceRepository.findById(claim.getMemberId());
        if (byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Insurance Id");
        }

        Optional<Diagnosis> byId1 = diagnosisRepository.findById(claim.getDiagnosisId());
        if (byId1.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Diagnosis Id");
        }

        Claim map = Claim.builder()
                .id(new ObjectId())
                .firstName(claim.getFirstName())
                .lastName(claim.getLastName())
                .dob(claim.getDob())
                .gender(claim.getGender())
                .phoneNumber(claim.getPhoneNumber())
                .email(claim.getEmail())
                .claimType(claim.getClaimType())
                .amount(claim.getAmount())
                .treatmentDate(claim.getTreatmentDate())
                .receipt(claim.getReceipt()).build();

        map.setMember(byId.orElse(null));
        map.setDiagnosis(byId1.orElse(null));

        return claimRepository.save(map);

    }

    @Override
    public void deleteClaim(String email) {
        Optional<Claim> byId = claimRepository.findById(email);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find Claim");
        }
        claimRepository.deleteById(email);
    }

    @Override
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    @Override
    public Claim updateClaim(String email, ClaimDTO claim) {

        Optional<Claim> byId = claimRepository.findById(email);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Claim not found");
        }

        Optional<Diagnosis> byId1 = diagnosisRepository.findById(claim.getDiagnosisId());
        if (byId1.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Diagnosis Id");
        }

        Optional<Insurence> byId2 = insurenceRepository.findById(claim.getMemberId());
        if (byId2.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Insurance Id");
        }


        Claim claim1 = byId.get();
        claim1.setFirstName(claim.getFirstName());
        claim1.setLastName(claim.getLastName());
        claim1.setDob(claim.getDob());
        claim1.setGender(claim.getGender());
        claim1.setPhoneNumber(claim.getPhoneNumber());
        claim1.setClaimType(claim.getClaimType());
        claim1.setAmount(claim.getAmount());

        claim1.setTreatmentDate(claim.getTreatmentDate());
        claim1.setReceipt(claim.getReceipt());

        claim1.setMember(byId2.orElse(null));
        claim1.setDiagnosis(byId1.orElse(null));

        return claimRepository.save(claim1);

    }

    @Override
    public void approveClaim(String email) {
        Optional<Claim> byId = claimRepository.findById(email);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Claim not found");
        }

        Claim claim = byId.get();
        claim.setApproved(true);

        claimRepository.save(claim);

    }

}