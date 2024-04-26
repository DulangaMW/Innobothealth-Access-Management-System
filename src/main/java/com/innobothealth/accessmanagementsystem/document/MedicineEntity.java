package com.innobothealth.accessmanagementsystem.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "medicine_details")
public class MedicineEntity {
    @Id
    private String id;
    @Indexed(unique=true)
    private Long Stockid;
    private String medicineType;
    private String medicineName;
    private String supplier;
    private String expireDate;
    private Integer quantity;
    private Double unitPrice;
}

