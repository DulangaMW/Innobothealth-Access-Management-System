package com.innobothealth.accessmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SMSDTO {

    private String recipient;
    private String sender_id;
    private String type;
    private String message;

}
