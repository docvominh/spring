package com.vominh.example.spring.mongo.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class ResponseError {
    private int status;
    private String code;
    private String message;
    private List<String> details;
}
