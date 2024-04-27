package com.innobothealth.accessmanagementsystem.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.innobothealth.accessmanagementsystem.document.Notification;
import com.innobothealth.accessmanagementsystem.document.User;
import com.innobothealth.accessmanagementsystem.dto.GetNotificationDTO;
import com.innobothealth.accessmanagementsystem.dto.MyNotificationDTO;
import com.innobothealth.accessmanagementsystem.dto.NotificationDTO;
import com.innobothealth.accessmanagementsystem.dto.NotificationReply;
import com.innobothealth.accessmanagementsystem.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notification")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @PostMapping("send")
    public void sendNotification(@RequestBody @Validated NotificationDTO notificationDTO) {
        notificationService.sendNotification(notificationDTO);
    }

    @GetMapping("getAll")
    public List<GetNotificationDTO> getSentNotifications(@AuthenticationPrincipal User user) {
        return notificationService.getNotifications(user.getId());
    }

    @PutMapping("acknowledge/{id}")
    public void acknowledgeNotification(@AuthenticationPrincipal User user, @PathVariable String id) {
        notificationService.acknowledgeNotification(user.getId(), id);
    }

    @PostMapping("reply/{id}")
    public void replyNotification(@AuthenticationPrincipal User user, @RequestBody JsonNode jsonNode, @PathVariable String id) {
        notificationService.replyNotification(user.getId(), jsonNode.get("reply").asText(), id);
    }

    @GetMapping("reply/get/{id}")
    public List<NotificationReply> getReply(@AuthenticationPrincipal User user, @PathVariable String id) {
        return notificationService.getReply(user.getId(), id);
    }

    @GetMapping("get/mynotification")
    public List<MyNotificationDTO> getAcknowledgedNotifications(@AuthenticationPrincipal User user) {
        return notificationService.getMyNotification(user.getId());
    }

    @DeleteMapping("delete/{id}")
    public void deleteNotification(@AuthenticationPrincipal User user, @PathVariable String id) {
        notificationService.deleteNotification(user.getId(), id);
    }

}
