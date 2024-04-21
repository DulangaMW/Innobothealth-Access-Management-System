package com.innobothealth.accessmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDTO {

    @NotNull
    private String category;
    @NotNull
    private String receiverType;
    @NotNull
    private String receiver;
    @NotNull
    private String subject;
    @NotNull
    private String message;
    @NotNull
    private String isAnonymous;
    @NotNull
    private String priority;
    @NotNull
    private String isScheduled;
    private String scheduledDateTime;

}
