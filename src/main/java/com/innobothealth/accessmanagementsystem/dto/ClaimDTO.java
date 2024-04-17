package com.innobothealth.accessmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClaimDTO {

    private String firstName;
    private String lastName;
    private ObjectId memberId;
    private Date dob;
    private String gender;
    private String phoneNumber;
    private String email;
    private String claimType;
    private int amount;
    private ObjectId diagnosisId;
    private Date treatmentDate;
    private byte[] receipt;

}
