package com.vominh.example.spring.mongo.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeModel extends GenericModel {
    private String officeId;
    private String name;
    private String address;

}
