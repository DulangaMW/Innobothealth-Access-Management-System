package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.Insurence;
import com.innobothealth.accessmanagementsystem.dto.InsuranceDTO;
import com.innobothealth.accessmanagementsystem.repository.InsurenceRepository;
import com.innobothealth.accessmanagementsystem.service.InsuranceService;
import org.springframework.stereotype.Service;

@Service
public class InsuranceServiceImpl implements InsuranceService {

    private final InsurenceRepository insurenceRepository;

    public InsuranceServiceImpl(InsurenceRepository insurenceRepository) {
        this.insurenceRepository = insurenceRepository;
    }

    @Override
    public Insurence createInsurence(InsuranceDTO insurence) {

        Insurence build = Insurence.builder()
                .memberId(insurence.getMemberId())
                .address(insurence.getAddress())
                .name(insurence.getName())
                .phoneNumber(insurence.getPhoneNumber())
                .payerId(insurence.getPayerId())
                .activeStatus(insurence.getActiveStatus())
                .city(insurence.getCity())
                .state(insurence.getState())
                .zip(insurence.getZip()).build();

        return insurenceRepository.save(build);

    }

}
