package com.innobothealth.accessmanagementsystem.repository;

import com.innobothealth.accessmanagementsystem.document.UserReplyNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserReplyNotificationRepository extends MongoRepository<UserReplyNotification, String> {

    List<UserReplyNotification> findAllByUserIdAndNotificationId(String userId, String notificationId);
}
