package com.innobothealth.accessmanagementsystem.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NotificationRunnableTask implements Runnable{

    private final String message;
    private final String subject;
    private final String email;
    private final String recipient;
    private final SMSSender smsSender;
    private final EmailSender emailSender;

    @Override
    public void run() {
        if (message != null && !message.isEmpty()) {
            smsSender.sendNotification(message, recipient);
        }
        if (subject != null && !subject.isEmpty() && email != null && !email.isEmpty()) {
            emailSender.sendEmail(email, subject, message);
        }
    }

}
