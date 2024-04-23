package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.Code;
import com.innobothealth.accessmanagementsystem.dto.CodeDTO;

import java.util.List;

public interface CodeService {

    Code createCode(CodeDTO code);
    void deleteCode(String codeId);
    List<Code> getAllCodes();
    Code updateCode(String codeId, CodeDTO code);
    void approveCode(String id);
}
