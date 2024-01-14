package com.innobothealth.accessmanagementsystem.util;

import com.innobothealth.accessmanagementsystem.dto.SMSDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    public void sendSMS(String message, String phoneNumber) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        SMSDTO body = SMSDTO.builder().message(message)
                .phoneNumber(phoneNumber).build();

        HttpEntity<SMSDTO> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Email Sending Failed!");
        }

    }

}
