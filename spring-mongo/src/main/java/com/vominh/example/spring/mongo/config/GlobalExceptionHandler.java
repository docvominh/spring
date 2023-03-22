package com.vominh.example.spring.mongo.config;

import com.vominh.example.spring.mongo.exception.BadRequestException;
import com.vominh.example.spring.mongo.exception.ResourceNotFoundException;
import com.vominh.example.spring.mongo.exception.ResponseError;
import com.vominh.example.spring.mongo.exception.UserExistedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ResponseError> resourceNotFound(Exception exception) {
        var errorBody = ResponseError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(errorBody);
    }

    @ExceptionHandler({BadRequestException.class, UserExistedException.class})
    public ResponseEntity<ResponseError> badRequestException(Exception exception) {
        var errorBody = ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(errorBody);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseBody
    public ResponseEntity<ResponseError> userNotFoundException(Exception exception) {
        var errorBody = ResponseError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorBody);
    }

    @ExceptionHandler({AuthenticationException.class, AuthenticationCredentialsNotFoundException.class, BadCredentialsException.class})
    @ResponseBody
    public ResponseEntity<ResponseError> authenticationException(Exception exception) {
        var errorBody = ResponseError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(errorBody);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Exception> exception(Exception exception) {
//        return ResponseEntity.internalServerError().body(exception);
//    }
}
