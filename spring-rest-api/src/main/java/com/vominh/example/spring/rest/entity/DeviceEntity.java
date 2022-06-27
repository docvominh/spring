package com.vominh.example.spring.rest.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "device")
@Data
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "device_name")
    private String name;

    @Column(name = "manufacture")
    private String manufacture;

    @Column(name = "price")
    private Integer price;


}
