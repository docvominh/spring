package com.vominh.example.spring.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private int serial;
    private String category;
    private String name;
}
