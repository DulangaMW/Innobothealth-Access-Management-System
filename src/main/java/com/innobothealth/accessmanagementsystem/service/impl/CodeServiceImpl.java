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

        Code map = Code.builder()
                .codeType(code.getCodeType())
                .codeName(code.getCodeName())
                .codeTitle(code.getCodeTitle())
                .description(code.getDescription())
                .build();

        return codeRepository.save(map);

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

        Optional<Code> byId = codeRepository.findById(id);
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code not found");
        }

        Code code1 = byId.get();
        code1.setCodeType(code.getCodeType());
        code1.setCodeName(code.getCodeName());
        code1.setCodeTitle(code.getCodeTitle());
        code1.setDescription(code.getDescription());

        return codeRepository.save(code1);

    }

}
