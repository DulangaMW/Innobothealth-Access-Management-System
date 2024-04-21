package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.dto.NotificationDTO;

public interface NotificationService {
    void sendNotification(NotificationDTO notification);
}
