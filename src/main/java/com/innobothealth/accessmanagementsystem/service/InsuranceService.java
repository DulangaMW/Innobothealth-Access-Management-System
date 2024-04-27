package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.Insurence;
import com.innobothealth.accessmanagementsystem.dto.InsuranceDTO;

import java.util.List;

public interface InsuranceService {

    Insurence createInsurence(InsuranceDTO insurence);
    Insurence updateInsurance(InsuranceDTO insurence, String id);
    void deleteInsurance(String id);
    List<Insurence> getAllInsurances();

}
