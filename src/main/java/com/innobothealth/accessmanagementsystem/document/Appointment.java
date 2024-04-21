package com.innobothealth.accessmanagementsystem.document;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.ToString;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
public class Appointment {
    @Id
    private String appointmentId;
    private String firstname;
    private String lastname;
    private String date;
    private String doctors_specialization;
    private String mail;
    private String member_id;
    private String phone_number;
    private String special_message;
    private String doctor_id;


}
