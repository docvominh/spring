package com.vominh.example.spring.mongo.data.document;

import com.vominh.example.spring.mongo.data.object.Address;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
// Put collection name here, or it will create new collection by Class name
@Document("users")
public class UserDocument {

    @Id
    private String docId;
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private OfficeDocument office;
    private List<Address> addresses;
}

