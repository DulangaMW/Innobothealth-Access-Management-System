package com.innobothealth.accessmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceDTO {

    @NotNull
    private String memberId;
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String payerId;
    @NotNull
    private String activeStatus;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String zip;

}
