package com.innobothealth.accessmanagementsystem.service.impl;

import com.innobothealth.accessmanagementsystem.document.Notification;
import com.innobothealth.accessmanagementsystem.document.User;
import com.innobothealth.accessmanagementsystem.document.UserReplyNotification;
import com.innobothealth.accessmanagementsystem.dto.NotificationDTO;
import com.innobothealth.accessmanagementsystem.dto.NotificationReply;
import com.innobothealth.accessmanagementsystem.repository.NotificationRepository;
import com.innobothealth.accessmanagementsystem.repository.UserReplyNotificationRepository;
import com.innobothealth.accessmanagementsystem.repository.UserRepository;
import com.innobothealth.accessmanagementsystem.service.NotificationService;
import com.innobothealth.accessmanagementsystem.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final EmailSender emailSender;
    private final SMSSender smsSender;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final UserReplyNotificationRepository userReplyNotificationRepository;

    public NotificationServiceImpl(EmailSender emailSender, SMSSender smsSender, UserRepository userRepository, NotificationRepository notificationRepository, ThreadPoolTaskScheduler threadPoolTaskScheduler, UserReplyNotificationRepository userReplyNotificationRepository) {
        this.emailSender = emailSender;
        this.smsSender = smsSender;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.userReplyNotificationRepository = userReplyNotificationRepository;
    }

    @Override
    public void sendNotification(NotificationDTO notification) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Notification.NotificationBuilder builder = Notification.builder();
        builder.category(notification.getCategory());
        builder.senderId(user.getId());
        builder.receiverType(notification.getReceiverType());
        builder.subject(notification.getSubject());
        builder.priority(notification.getPriority());

        Map<String, String> userMap = new HashMap<>();
        List<NotificationPref> notificationPref = new ArrayList<>();
        switch (notification.getReceiverType()) {
            case "ADMIN" :
                Optional<User> byId = userRepository.findById(notification.getReceiver());
                if (!byId.isPresent() || !byId.get().getRole().equals(Role.ADMIN)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid receiver");
                }
                userMap.put("email", byId.get().getEmail());
                userMap.put("number", byId.get().getMobileNumber());
                userMap.put("name", byId.get().getFirstName());
                notificationPref=byId.get().getNotificationPreference();
                builder.channels(notificationPref);
                builder.receiverId(byId.get().getId());
                break;
            case "STAFF" :
                Optional<User> byId2 = userRepository.findById(notification.getReceiver());
                if (!byId2.isPresent() || !byId2.get().getRole().equals(Role.STAFF)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid receiver");
                }
                userMap.put("email", byId2.get().getEmail());
                userMap.put("number", byId2.get().getMobileNumber());
                userMap.put("name", byId2.get().getFirstName());
                notificationPref=byId2.get().getNotificationPreference();
                builder.channels(notificationPref);
                builder.receiverId(byId2.get().getId());
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid receiver type");
        }

        String message = createMessage(userMap.get("name"), notification.getSubject(), notification.getMessage(),
                Boolean.parseBoolean(notification.getIsAnonymous()), notification.getPriority(), user.getFirstName());
        builder.message(message);

        if (!Boolean.parseBoolean(notification.getIsScheduled())) {
            if (notificationPref.contains(NotificationPref.SMS)) {
                smsSender.sendNotification(message, userMap.get("number"));
                builder.deliveredTime(LocalDateTime.now());
            }
            if (notificationPref.contains(NotificationPref.EMAIL)) {
                emailSender.sendEmail(userMap.get("email"), notification.getSubject(), message);
            }
            builder.isAcknowledged(false);
            notificationRepository.save(builder.build());
        } else {
            NotificationRunnableTask notificationRunnableTask = new NotificationRunnableTask(message, notificationPref.contains(NotificationPref.EMAIL) ? notification.getSubject() : null,
                    notificationPref.contains(NotificationPref.EMAIL) ? userMap.get("email") : null, notificationPref.contains(NotificationPref.EMAIL) ? userMap.get("number") : null,
                    smsSender, emailSender);
            Instant instant = LocalDateTime.parse(notification.getScheduledDateTime()).atZone(ZoneId.of("Asia/Colombo")).toInstant();
            Date startTime = Date.from(instant);
            threadPoolTaskScheduler.schedule(notificationRunnableTask, startTime);
            builder.deliveredTime(LocalDateTime.parse(notification.getScheduledDateTime()));
            builder.isAcknowledged(false);
            notificationRepository.save(builder.build());
        }

    }

    @Override
    public List<Notification> getNotifications(String userId) {
        return notificationRepository.findAllBySenderId(userId);
    }

    @Override
    public void acknowledgeNotification(String userId, String notificationId) {
        Notification byIdAndReceiverId = notificationRepository.findByIdAndReceiverId(notificationId, userId);
        if (byIdAndReceiverId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid notification");
        }
        byIdAndReceiverId.setAcknowledged(true);
        notificationRepository.save(byIdAndReceiverId);
    }

    @Override
    public void replyNotification(String userId, String reply, String notificationId) {
        Notification byIdAndReceiverId = notificationRepository.findByIdAndReceiverId(notificationId, userId);
        if (byIdAndReceiverId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid notification");
        }
        UserReplyNotification build = UserReplyNotification.builder()
                .notificationId(notificationId)
                .userId(userId)
                .reply(reply).build();

        userReplyNotificationRepository.save(build);
    }

    @Override
    public List<NotificationReply> getReply(String userId, String notificationId) {
        Notification byIdAndReceiverId = notificationRepository.findByIdAndReceiverId(notificationId, userId);
        if (byIdAndReceiverId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid notification");
        }

        List<NotificationReply> notificationReplies = new ArrayList<>();
        userReplyNotificationRepository.findAllByUserIdAndNotificationId(userId, notificationId).stream().forEach(userReplyNotification -> {
            notificationReplies.add(
                    NotificationReply.builder()
                            .id(userReplyNotification.getId())
                            .notificationId(userReplyNotification.getNotificationId())
                            .userId(userReplyNotification.getUserId())
                            .firstName(userRepository.findById(userReplyNotification.getUserId()).get().getFirstName())
                            .lastName(userRepository.findById(userReplyNotification.getUserId()).get().getLastName())
                            .reply(userReplyNotification.getReply())
                            .build()
            );
        });
        return notificationReplies;
    }


    private String createMessage(String receiver, String subject, String message, boolean anonymous, String priority, String firstName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Priority: ").append(priority).append("\n\n*").append(subject).append("*\n\n")
                .append("Dear ").append(receiver).append(",\n")
                .append(message).append("\n\n").append("Many Thanks");
        if (!anonymous) {
            stringBuilder.append(",\n").append(firstName);
        }
        stringBuilder.append(".");
        return stringBuilder.toString();
    }
}
