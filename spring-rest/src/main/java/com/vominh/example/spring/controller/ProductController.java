package com.vominh.example.spring.controller;

import com.vominh.example.spring.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    private List<Product> products;

    public ProductController() {
        products = new ArrayList<>();
        products.add(Product.builder().serial(11111).category("computer").name("Dell XPS 13").build());
        products.add(Product.builder().serial(22222).category("computer").name("Dell XPS 15").build());
        products.add(Product.builder().serial(33333).category("computer").name("Mac 15 Retina").build());
        products.add(Product.builder().serial(44444).category("mobile").name("G-Phone").build());
        products.add(Product.builder().serial(55555).category("mobile").name("B-Phone").build());
    }

    @GetMapping(value = "/all")
    public ResponseEntity all() {
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity filterByCategory(@RequestParam("category") String category) {
        return ResponseEntity.status(HttpStatus.OK).body(products.stream().filter(c -> c.getCategory().equals(category)));
    }

}
