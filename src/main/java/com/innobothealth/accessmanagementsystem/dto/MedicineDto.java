package com.innobothealth.accessmanagementsystem.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MedicineDto {
    private Long Stockid;
    private String medicineName;
    private String medicineType;
    private String supplier;
    private String expireDate;
    private Integer quantity;
    private Double unitPrice;

}
