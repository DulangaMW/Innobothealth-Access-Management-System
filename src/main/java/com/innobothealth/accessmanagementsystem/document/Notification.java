package com.innobothealth.accessmanagementsystem.document;

import com.innobothealth.accessmanagementsystem.util.NotificationPref;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    private String id;
    private String category;
    private String senderId;
    private String receiverType;
    private String receiverId;
    private String subject;
    private String message;
    private LocalDateTime deliveredTime;
    private String priority;
    private List<NotificationPref> channels;
}
