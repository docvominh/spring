package com.vominh.example.spring.mongo.data.object;

import lombok.Data;

@Data
public class Address {
    private String country;
    private String city;
    private String zipcode;
    private String streetAndHouseNumber;
}
