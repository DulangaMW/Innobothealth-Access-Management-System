package com.innobothealth.accessmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorAndAvailability {

    private String id;
    private String firstName;
    private String lastName;
    private String specialization;
    private LocalDateTime availabilityFrom;
    private LocalDateTime availabilityTo;
    private boolean maxPatientsReached;

}
