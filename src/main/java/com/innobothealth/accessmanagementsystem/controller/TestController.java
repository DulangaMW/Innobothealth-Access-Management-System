package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.util.EmailSender;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private final EmailSender emailSender;

    public TestController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @GetMapping("say-hello")
    public String sayHello() {
        return "Hello InnobotHealth!!!";
    }

    @GetMapping("send-email")
    public String sendTestEmail(@RequestParam String email, @RequestParam String subject, @RequestParam String message) throws MessagingException {
        emailSender.sendEmail(email, subject,
                message);
        return "Email Sent";
    }



}
