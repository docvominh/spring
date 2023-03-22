package com.vominh.example.spring.mongo.data.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "offices")
public class OfficeDocument {
    @Id
    private String docId;
    private String officeId;
    private String name;
    private String address;
}
