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
public class Insurence {

    @Id
    private String id;

    private String memberId;
    private String name;
    private String address;
    private String phoneNumber;
    private String payerId;
    private String activeStatus;
    private String city;
    private String state;
    private String zip;

}
