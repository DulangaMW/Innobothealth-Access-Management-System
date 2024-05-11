package com.innobothealth.accessmanagementsystem.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "activity_logs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityLog {
    @Id
    private String id;
    private String userId;
    private String description;
    private LocalDateTime timestamp;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    // Add other fields as needed
}
