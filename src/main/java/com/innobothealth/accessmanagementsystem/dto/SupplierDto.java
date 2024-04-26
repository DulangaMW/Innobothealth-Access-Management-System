package com.innobothealth.accessmanagementsystem.dto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierDto {
    private String companyName;
    private String supplier_name;
    private String contact_person;
    private String phone;
    private String country;

}
