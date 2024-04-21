package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.dto.NotificationDTO;
import com.innobothealth.accessmanagementsystem.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


}
