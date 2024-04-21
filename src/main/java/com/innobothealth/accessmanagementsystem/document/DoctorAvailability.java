package com.innobothealth.accessmanagementsystem.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorAvailability {

    @Id
    private String id;
    private String doctorId;
    private LocalDateTime availabilityFrom;
    private LocalDateTime availabilityTo;
    private boolean maxPatientsReached = false;

}
