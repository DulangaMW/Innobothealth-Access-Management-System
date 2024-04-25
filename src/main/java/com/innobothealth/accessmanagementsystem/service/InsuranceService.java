package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.Insurence;
import com.innobothealth.accessmanagementsystem.dto.InsuranceDTO;

public interface InsuranceService {

    Insurence createInsurence(InsuranceDTO insurence);

}
