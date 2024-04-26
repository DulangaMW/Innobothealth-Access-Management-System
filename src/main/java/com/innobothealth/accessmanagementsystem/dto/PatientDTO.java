package com.innobothealth.accessmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    @NotNull
    private String patientName;
    @NotNull
    private int age;
    @NotNull
    private String gender;
    @NotNull
    private String dob;
    @NotNull
    private String country;
    @NotNull
    private String address;
    @NotNull
    private String email;

}
