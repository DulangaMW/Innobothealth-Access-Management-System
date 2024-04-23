package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.Code;
import com.innobothealth.accessmanagementsystem.repository.CodeRepository;
import com.innobothealth.accessmanagementsystem.dto.CodeDTO;
import com.innobothealth.accessmanagementsystem.service.CodeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class CodeServiceImpl implements CodeService {

    private final ModelMapper modelMapper;
    private final CodeRepository codeRepository;


    public CodeServiceImpl(ModelMapper modelMapper,CodeRepository codeRepository) {
        this.modelMapper = modelMapper;
        this.codeRepository = codeRepository;
    }

    @Override
    public Code createCode(CodeDTO code) {

        Optional<Code> byId1 = codeRepository.findById(code.getCodeId());
        if (byId1.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Diagnosis Id");
        }

        Code map = Code.builder()
                .codeType(code.getCodeType())
                .codeName(code.getCodeName())
                .codeTitle(code.getCodeTitle())
                .description(code.getDescription())
                .build();

        map.setCode(byId.orElse(null));
        map.setDiagnosis(byId1.orElse(null));

        return CodeRepository.save(map);

    }

    @Override
    public void deleteCode(String id) {
        Optional<Code> byId = codeRepository.findById(id);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find Code");
        }
        codeRepository.deleteById(id);
    }

    @Override
    public List<Code> getAllCodes() {
        return codeRepository.findAll();
    }

    @Override
    public Code updateCode(String id, CodeDTO code) {

        Optional<Code> byId = CodeRepository.findById(id);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code not found");
        }

        Optional<Code> byId1 = codeRepository.findById(code.getCodeId());
        if (byId1.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Code Id");
        }

        Optional<Insurence> byId2 = insurenceRepository.findById(code.getMemberId());
        if (byId2.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Insurance Id");
        }


        Code code1 = byId.get();
        code1.setCodeType(code.getCodeType());
        code1.setCodeName(code.getCodeName());
        code1.setCodeTitle(code.getCodeTitle());
        code1.setDescription(code.getDescription());


        code1.setMember(byId2.orElse(null));
        code1.setDiagnosis(byId1.orElse(null));

        return codeRepository.save(code1);

    }

    @Override
    public void approveCode(String id) {
        Optional<Code> byId = codeRepository.findById(id);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code not found");
        }

        Code code = byId.get();
        code.setApproved(true);

        codeRepository.save(code);

    }

}
