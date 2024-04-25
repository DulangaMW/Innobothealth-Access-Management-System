package com.innobothealth.accessmanagementsystem.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Claim {

    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String memberId;
    private Date dob;
    private String gender;
    private String phoneNumber;
    private String claimType;
    private int amount;
    private String diagnosisId;
    private Date treatmentDate;
    private byte[] receipt;


    private boolean isApproved = false;
    private User approvedBy;

}
