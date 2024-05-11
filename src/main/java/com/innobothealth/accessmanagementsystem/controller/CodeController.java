package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.document.Code;
import com.innobothealth.accessmanagementsystem.dto.CodeDTO;
import com.innobothealth.accessmanagementsystem.service.CodeService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("code")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CodeController {

    private final CodeService codeService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }


    @PostMapping("create")
    public ResponseEntity<Code> createCode(
            @RequestParam("codeType") @NotNull String codeType,
            @RequestParam("codeName") @NotNull String codeName,
            @RequestParam("codeTitle") @NotNull String codeTitle,
            @RequestParam("description") @NotNull String description) throws ParseException, IOException {

        CodeDTO build = CodeDTO.builder()
                .codeType(codeType)
                .codeName(codeName)
                .codeTitle(codeTitle)
                .description(description)
                .build();


        return ResponseEntity.status(201).body(codeService.createCode(build));

    }

    @DeleteMapping("delete")
    public void deleteCode(@RequestParam("id") @NotNull String id) {
        codeService.deleteCode(id);
    }

    @GetMapping("getAll")
    public List<Code> getAllCode() {
        return codeService.getAllCodes();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Code> updateCode(
            @PathVariable("id") @NotNull String id,
            @RequestParam("codeType") @NotNull String codeType,
            @RequestParam("codeName") @NotNull String codeName,
            @RequestParam("codeTitle") @NotNull String codeTitle,
            @RequestParam("description") @NotNull String description) throws ParseException, IOException {

        CodeDTO build = CodeDTO.builder()
                .codeType(codeType)
                .codeName(codeName)
                .codeTitle(codeTitle)
                .description(description)
                .build();

        return ResponseEntity.status(201).body(codeService.updateCode(id, build));

    }

}