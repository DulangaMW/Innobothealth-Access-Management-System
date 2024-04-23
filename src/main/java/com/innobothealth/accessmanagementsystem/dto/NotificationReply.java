package com.innobothealth.accessmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationReply {

    private String id;
    private String notificationId;
    private String userId;
    private String firstName;
    private String lastName;
    private String reply;

}
