package com.vominh.example.spring.mongo;

import com.vominh.example.spring.mongo.api.UserApi;
import com.vominh.example.spring.mongo.data.model.UserModel;
import com.vominh.example.spring.mongo.data.repository.IUserRepo;
import com.vominh.example.spring.mongo.exception.BadRequestException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @Autowired
    UserApi api;

    @Autowired
    IUserRepo repo;

    @BeforeAll
    void setUp() {
        repo.deleteAll();
    }

    @AfterAll
    void cleanUp(){
        repo.deleteAll();
    }

    @Test
    void signupTest() {
        var user = UserModel.builder()
                .name("Minh")
                .build();

        Assertions.assertThrows(BadRequestException.class, () -> api.signUp(user));

        Assertions.assertThrows(BadRequestException.class, () -> {
            user.setEmail("m@gmail.com");
            user.setPassword("1");
            user.setRePassword("2");
            api.signUp(user);
        });

        user.setRePassword("1");
        var response = api.signUp(user);
        Assertions.assertEquals("m@gmail.com", response.getBody().getEmail());
    }
}
