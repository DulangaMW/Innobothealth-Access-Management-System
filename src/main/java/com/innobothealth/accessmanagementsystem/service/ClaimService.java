package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.Claim;
import com.innobothealth.accessmanagementsystem.dto.ClaimDTO;

import java.util.List;

public interface ClaimService {

    Claim createClaim(ClaimDTO claim);
    void deleteClaim(String claimId);
    List<Claim> getAllClaims();
    Claim updateClaim(String claimId, ClaimDTO claim);
    void approveClaim(String id);
}
