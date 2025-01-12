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
@Document(collection = "Supplier_details")
public class SupplierEntity {
    @Id
    private String id;
    @Indexed(unique=true)
    private Long suplierid;
    private String supplier_name;
    private String contact_person;
    private String phone;
    private String country;

}
