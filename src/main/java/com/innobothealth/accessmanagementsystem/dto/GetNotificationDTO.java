package com.innobothealth.accessmanagementsystem.dto;

import com.innobothealth.accessmanagementsystem.util.NotificationPref;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetNotificationDTO {

    private String id;
    private String category;
    private String senderId;
    private String receiverType;
    private String receiverId;
    private String firstName;
    private String lastName;
    private String subject;
    private String message;
    private LocalDateTime deliveredTime;
    private String priority;
    private boolean isAcknowledged;
    private List<NotificationPref> channels;

}
