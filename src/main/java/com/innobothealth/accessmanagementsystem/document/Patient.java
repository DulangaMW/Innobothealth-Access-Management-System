package com.innobothealth.accessmanagementsystem.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {

    @Id
    private String id;

    private String patientName;
    private int age;
    private String gender;
    private String dob;
    private String country;
    private String address;
    private String email;

}
