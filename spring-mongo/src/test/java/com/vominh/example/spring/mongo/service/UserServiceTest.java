package com.vominh.example.spring.mongo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vominh.example.spring.mongo.config.security.JwtService;
import com.vominh.example.spring.mongo.data.document.UserDocument;
import com.vominh.example.spring.mongo.data.document.sequence.SequenceService;
import com.vominh.example.spring.mongo.data.mapper.UserMapper;
import com.vominh.example.spring.mongo.data.model.UserModel;
import com.vominh.example.spring.mongo.data.repository.IUserRepo;
import com.vominh.example.spring.mongo.exception.BadRequestException;
import com.vominh.example.spring.mongo.exception.UserExistedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    IUserRepo repo;
    UserService service;

    @BeforeAll
    void setUp() {
        repo = Mockito.mock(IUserRepo.class);
        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
        service = new UserService(repo, Mockito.mock(UserMapper.class), Mockito.mock(SequenceService.class), new BCryptPasswordEncoder(), authenticationManager, new JwtService(""), Mockito.mock(ObjectMapper.class));
    }

    @Test
    void signup_password_not_match() {
        var user = UserModel.builder()
                .name("Minh")
                .email("phamducminh1990@gmail.com")
                .password("123456")
                .rePassword("111111")
                .build();

        var e = Assertions.assertThrows(BadRequestException.class, () -> {
            service.signup(user);
        });

        Assertions.assertEquals("Password and RePassword not matched", e.getMessage());
    }

    @Test
    void empty_email() {
        var user = UserModel.builder()
                .name("Minh")
                .password("1")
                .rePassword("1")
                .build();

        var e = Assertions.assertThrows(BadRequestException.class, () -> {
            service.signup(user);
        });

        Assertions.assertEquals("Email can not be empty", e.getMessage());
    }

    @Test
    void email_existed() {
        var user = UserModel.builder()
                .name("Minh")
                .email("phamducminh1990@gmail.com")
                .password("1")
                .rePassword("1")
                .build();

        when(repo.findByEmail(any(String.class))).thenReturn(Optional.of(new UserDocument()));

        var e = Assertions.assertThrows(UserExistedException.class, () -> {
            service.signup(user);
        });

        Assertions.assertEquals("Email already used", e.getMessage());
    }
}
