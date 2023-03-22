package com.vominh.example.spring.mongo.exception;

public class UserExistedException extends RuntimeException {

    public UserExistedException(String message) {
        super(message);
    }
}