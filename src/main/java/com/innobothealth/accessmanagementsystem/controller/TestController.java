package com.innobothealth.accessmanagementsystem.controller;

import com.innobothealth.accessmanagementsystem.util.EmailSender;
import com.innobothealth.accessmanagementsystem.util.SMSSender;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {

    @Autowired
    private final EmailSender emailSender;

    @Autowired
    private SMSSender smsSender;

    public TestController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @GetMapping("say-hello")
    public String sayHello() {
        return "Updated Hello - 5!!";
    }

    @GetMapping("send-email")
    public String sendTestEmail(@RequestParam String email, @RequestParam String subject, @RequestParam String message) throws MessagingException {
        emailSender.sendEmail(email, subject,
                message);
        return "Email Sent";
    }

    @GetMapping("send-sms")
    public String sendTestSMS(@RequestParam String message, @RequestParam String phoneNumber) throws MessagingException {
        smsSender.sendOTP(message, phoneNumber);
        return "SMS Sent";
    }


}
