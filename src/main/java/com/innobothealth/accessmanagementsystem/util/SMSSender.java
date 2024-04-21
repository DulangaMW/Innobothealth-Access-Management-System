package com.innobothealth.accessmanagementsystem.util;

import com.innobothealth.accessmanagementsystem.dto.SMSDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SMSSender {

    @Value("${sms.api.token}")
    private String token;

    @Value("${sms.api.url}")
    private String url;


    private final RestTemplate restTemplate;


    public SMSSender(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async("asyncPool")
    public void sendOTP(String otp, String phoneNumber) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer ".concat(token));
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        SMSDTO body = SMSDTO.builder().message("Your OTP for https://innobot.dulanga.com is : ".concat(otp))
                .recipient(phoneNumber.replaceFirst("0", "+94"))
                .sender_id("FitSMS Demo")
                .type("plain")
                .build();

        HttpEntity<SMSDTO> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("OTP Sending Failed!");
        }

    }

    @Async("asyncPool")
    public void sendNotification(String message, String phoneNumber) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer ".concat(token));
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        SMSDTO body = SMSDTO.builder().message(message)
                .recipient(phoneNumber.replaceFirst("0", "+94"))
                .sender_id("FitSMS Demo")
                .type("plain")
                .build();

        HttpEntity<SMSDTO> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Notification Sending Failed!");
        }

    }



}
