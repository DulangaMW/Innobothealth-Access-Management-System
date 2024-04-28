package com.innobothealth.accessmanagementsystem.service;

import com.innobothealth.accessmanagementsystem.document.Notification;
import com.innobothealth.accessmanagementsystem.dto.GetNotificationDTO;
import com.innobothealth.accessmanagementsystem.dto.MyNotificationDTO;
import com.innobothealth.accessmanagementsystem.dto.NotificationDTO;
import com.innobothealth.accessmanagementsystem.dto.NotificationReply;

import java.util.List;

public interface NotificationService {
    void sendNotification(NotificationDTO notification);
    List<GetNotificationDTO> getNotifications(String userId);
    void acknowledgeNotification(String userId, String notificationId);
    void replyNotification(String userId, String reply, String notificationId);
    List<NotificationReply> getReply(String userId, String notificationId);
    List<MyNotificationDTO> getMyNotification(String userId);
    void deleteNotification(String userId, String notificationId);

    void updateNotification(String userId, String id, String subject, String message, boolean isAnonymous, String priority, boolean isScheduled, String scheduledDateTime);
}
