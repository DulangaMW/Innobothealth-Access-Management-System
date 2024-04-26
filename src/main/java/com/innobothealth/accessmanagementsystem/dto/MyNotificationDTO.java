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
public class MyNotificationDTO {

    private String id;
    private String senderFistName;
    private String senderLastName;
    private String subject;
    private String message;
    private LocalDateTime deliveredTime;
}
