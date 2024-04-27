package com.innobothealth.accessmanagementsystem.repository;

import com.innobothealth.accessmanagementsystem.document.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findAllBySenderId(String receiver);
    Notification findByIdAndReceiverId(String id, String receiverId);
    List<Notification> findAllByReceiverIdAndIsAcknowledged(String receiverId, boolean acknowledged);
    Notification findByIdAndSenderId(String id, String senderId);
}
