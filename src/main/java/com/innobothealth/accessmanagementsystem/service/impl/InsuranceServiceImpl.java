package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.Insurence;
import com.innobothealth.accessmanagementsystem.dto.InsuranceDTO;
import com.innobothealth.accessmanagementsystem.repository.InsurenceRepository;
import com.innobothealth.accessmanagementsystem.service.InsuranceService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Insurence updateInsurance(InsuranceDTO insurence, String id) {
        Optional<Insurence> byId = insurenceRepository.findById(id);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insurance not found");
        }
        Insurence build = byId.get();
        build.setMemberId(insurence.getMemberId());
        build.setAddress(insurence.getAddress());
        build.setName(insurence.getName());
        build.setPhoneNumber(insurence.getPhoneNumber());
        build.setPayerId(insurence.getPayerId());
        build.setActiveStatus(insurence.getActiveStatus());
        build.setCity(insurence.getCity());
        build.setState(insurence.getState());
        build.setZip(insurence.getZip());
        return insurenceRepository.save(build);

    }

    @Override
    public void deleteInsurance(String id) {
        Optional<Insurence> byId = insurenceRepository.findById(id);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insurance not found");
        }
        insurenceRepository.delete(byId.get());
    }

    @Override
    public List<Insurence> getAllInsurances() {
        return insurenceRepository.findAll();
    }

}
